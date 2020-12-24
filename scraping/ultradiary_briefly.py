from urllib.request import urlopen
from bs4 import BeautifulSoup
import requests
import csv

base_url = "http://ultradiary.com/linediary.html?page="
data_num = 0
#277
for num in range(1, 278) :

    html = urlopen(base_url + str(num))
    bs = BeautifulSoup(html, 'html.parser')

    diarys = bs.findAll('div', {'style' : "margin-bottom:5px; margin-top:4px; margin-left:5px; line-height:1.2em; word-break:break-all; font-family:돋움; font-size: 9pt; color: #404040;"})

    for diary in diarys :
        
        data_num += 1

        data = {
            'id' : data_num,
            'diary' : diary.get_text()
        }

        with open("ultradiary_briefly.csv", "a", encoding='utf-8', newline="") as csvfile :
            fieldnames = ["id", "diary"]
            csvwriter = csv.DictWriter(csvfile, fieldnames=fieldnames)
            csvwriter.writerow(data)

        print(str(data_num) + ' 개 저장 완료!')