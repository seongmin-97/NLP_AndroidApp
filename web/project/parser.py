# coding: utf-8

import csv
read_list = []

f = open('./naver_movie_dataset.csv', 'r', encoding='utf-8-sig')
reader = csv.DictReader(f)
import os

os.environ.setdefault("DJANGO_SETTINGS_MODULE", "project.settings")
import django
django.setup()

from restapi.models import movieTitle
if __name__=='__main__':
    for row in reader:
        movieTitle(title = row['title']).save()
        print(row['title'] + 'is saved!')