from django.db import models

# Create your models here.

class Movie(models.Model) :
    title = models.CharField('title', max_length=20)
    genre = models.CharField('genre', max_length=10)
    year = models.CharField('year', max_length=5)
    date = models.CharField('date', max_length=10)
    rating = models.CharField('rating', max_length=10)
    vote_count = models.CharField('vote_count', max_length=10)
    plot = models.TextField('plot')
    main_act = models.CharField('main_act', max_length=20)
    supp_act = models.CharField('supp_act', max_length=20)
    page_url = models.URLField('page_url')
    img_url = models.URLField('img_url')

class movieTitle(models.Model) :
    title = models.CharField('title', max_length=20)

class movieInfo(models.Model) :
    title = models.CharField('title', max_length=20)
    titleNoSpace = models.CharField('titleNoSpace', max_length=20)
    genre = models.CharField('genre', max_length=10)
    year = models.CharField('year', max_length=5)
    date = models.CharField('date', max_length=10)
    rating = models.CharField('rating', max_length=10)
    vote_count = models.CharField('vote_count', max_length=10)
    plot = models.TextField('plot')
    main_act = models.CharField('main_act', max_length=20)
    supp_act = models.CharField('supp_act', max_length=20)
    page_url = models.URLField('page_url')
    img_url = models.URLField('img_url')