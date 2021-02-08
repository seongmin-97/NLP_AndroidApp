from rest_framework import serializers
from .models import Movie, movieTitle, movieInfo, sentiment

class MovieSerializer(serializers.ModelSerializer) :
    class Meta :
        model = Movie
        fields = ('title', 'genre', 'year', 'date', 'rating', 'vote_count', 'plot', 'main_act', 'supp_act', 'page_url', 'img_url')

class movieTitleSerializer(serializers.ModelSerializer) :
    class Meta :
        model = movieTitle
        fields = ('title',)

class movieInfoSerializer(serializers.ModelSerializer) :
    class Meta :
        model = movieInfo
        fields = ('title', 'titleNoSpace', 'genre', 'year', 'date', 'rating', 'vote_count', 'plot', 'main_act', 'supp_act', 'page_url', 'img_url')

class sentimentSerializer(serializers.ModelSerializer) :
    class Meta :
        model = sentiment
        fields = ('rating',)