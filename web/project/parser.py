# coding: utf-8

import csv
read_list = []

f = open('./naver_movie_dataset.csv', 'r', encoding='utf-8-sig')
reader = csv.DictReader(f)
import os

os.environ.setdefault("DJANGO_SETTINGS_MODULE", "project.settings")
import django
django.setup()

from restapi.models import movieInfo
if __name__=='__main__':
    for row in reader:
        movieInfo(title = row['title'], titleNoSpace = row['title'].replace(' ', ''), genre = row['genre'], year = row['year'], date = row['date'], rating = row['rating'], vote_count = row['vote_count'], plot = row['plot'], main_act = row['main_act'], supp_act = row['supp_act'], page_url = row['page_url'], img_url = row['img_url']).save()
        print(row['title'] + 'is saved!')