from urllib.request import urlopen
from bs4 import BeautifulSoup
import requests
import csv

base_url = "https://movie.naver.com/movie/sdb/rank/rmovie.nhn?sel=pnt&tg=1&date=20210114&page="

for page in range(1, 3) :

    html = urlopen(base_url+str(page))
    bs = BeautifulSoup(html, 'html.parser')

    div_list = bs.findAll('div', {'class' : 'tit5'})

    for div in div_list :
        
        href = div.find('a').attrs['href']
        
        html = urlopen("https://movie.naver.com"+href)
        bs = BeautifulSoup(html, 'html.parser')

        

        try :
            href = href[12:]
            title = bs.find('a', {'href' : '.' + href}).get_text()
        except :
            continue
        
        genre = "드라마"

        try :
            story = bs.find('p', {'class' : 'con_tx'}).get_text()
        except :
            story = None

        try :
            em_list = bs.find('div', {'class' : 'star_score'}).findAll('em')
            score = []

            for em in em_list :
                score.append(em.get_text())

            score = ''.join(score)
        except :
            score = None

        print(title)
        print("\n")
        print(score)
        print("\n")
        