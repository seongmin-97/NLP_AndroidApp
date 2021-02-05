from rest_framework import serializers
from .models import Movie

class MovieSerializer(serializers.ModelSerializer) :
    class Meta :
        model = Movie
        fields = ('title', 'genre', 'year', 'date', 'rating', 'vote_count', 'plot', 'main_act', 'supp_act', 'page_url', 'img_url')