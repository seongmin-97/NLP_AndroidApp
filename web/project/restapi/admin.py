from django.contrib import admin
from .models import Movie, movieTitle, movieInfo, sentiment

# Register your models here.
admin.site.register(Movie)
admin.site.register(movieTitle)
admin.site.register(movieInfo)
admin.site.register(sentiment)