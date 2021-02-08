from django.shortcuts import render, redirect
from .models import Movie, movieTitle, movieInfo
from .serializers import MovieSerializer, movieTitleSerializer, movieInfoSerializer
from rest_framework.response import Response
from rest_framework.decorators import api_view


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