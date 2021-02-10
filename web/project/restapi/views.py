from django.shortcuts import render, redirect
from .models import Movie, movieTitle, movieInfo, sentiment
from .serializers import MovieSerializer, movieTitleSerializer, movieInfoSerializer, sentimentSerializer
from rest_framework.response import Response
from rest_framework.decorators import api_view
import csv
import os
from tensorflow.keras.models import load_model
from konlpy.tag import Okt
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences

# Create your views here.

@api_view(['GET'])
def movie(request, title) :
    search = title.replace(' ', '')
    getMovie = movieInfo.objects.filter(titleNoSpace__contains=search)
    result = movieInfoSerializer(getMovie, many=True)
    return Response(result.data)

@api_view(['GET'])
def allMovies(request) :
    getMovie = movieTitle.objects.all()
    result = movieTitleSerializer(getMovie, many=True)
    return Response(result.data)

@api_view(['GET'])
def emotion(request, rating) :
    getSentiment = sentiment.objects.filter(rating=rating)
    result = sentimentSerializer(getSentiment, many=True)
    return Response(result.data)

@api_view(['GET'])
def movie_Info(request, title) :
    search = title.replace(' ', '')
    getMovie = movieInfo.objects.filter(titleNoSpace=search)
    result = movieInfoSerializer(getMovie, many=True)
    return Response(result.data)

@api_view(['GET'])
def predict(request) :
    if request.method == "GET" :
        review = request.GET["review"]
        print("get" + str(type(review)))
        loaded_model = load_model('./restapi/model.h5')
        tokenizer = Tokenizer(19417, oov_token = 'OOV') ######## vocab_size = 19417
        file= open(r'./restapi/xtrain.csv','r') #개인별 경로지정
        data= csv.reader(file)
        data= list(data)
        tokenizer.fit_on_texts(data)
        print("tokenizer____________________________________")
        okt = Okt()
        new_sentence = okt.morphs(review, stem=True) # 토큰화
        stopwords = ['의','가','이','은','들','는','좀','잘','걍','과','도','를','으로','자','에','와','한','하다']
        new_sentence = [word for word in new_sentence if not word in stopwords] # 불용어 제거
        encoded = tokenizer.texts_to_sequences([new_sentence]) # 정수 인코딩
        pad_new = pad_sequences(encoded, maxlen = 30) # 패딩
        score = float(loaded_model.predict(pad_new)) # 예측
        print("finish____________________________________")
        if(score > 0.5):
            return redirect('emotion/1')
        else:
            return redirect('emotion/0')