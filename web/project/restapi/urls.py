from django.urls import path
from . import views

urlpatterns = [
    path('movie/<str:title>', views.movie, name='movie'),
    path('movieInfo/<str:title>', views.movie_Info, name='movieInfo'),
    path('allMovies/', views.allMovies, name='allMovies'),
    path('emotion/<int:rating>', views.emotion, name='emotion'),
]