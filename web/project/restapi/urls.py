from django.urls import path
from . import  views

urlpatterns = [
    path('movie/<str:title>', views.movie, name='movie')
]