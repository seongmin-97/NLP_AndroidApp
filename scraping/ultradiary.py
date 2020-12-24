from urllib.request import urlopen
from bs4 import BeautifulSoup
import requests
import csv

data_num = 0
base_url = "http://ultradiary.com"

for number in range(1683) :
    
    url = "/opendiary_list.html?c=&page="

    html = urlopen(base_url+url+str(number))
    bs = BeautifulSoup(html, 'html.parser')

    tds = bs.findAll('tr', {'height' : '25'})

    for td in tds :

        href = td.find('a').attrs['href']

        list_href = list(href)
        list_href[4:17] = ''
        list_href[19:27] = '?no='
        href = ''.join(list_href)
        
        try :
            html = urlopen(base_url+href)
            Bs = BeautifulSoup(html, 'html.parser')
        except :
            continue
        
        title = Bs.find('b').get_text()
        diary = Bs.find('td', {'class': 'doc'}).get_text()

        data = {
            "title": title,
            "diary": diary
        }

        with open("ultradiary.csv", "a", encoding='utf-8', newline="") as csvfile :
            fieldnames = ["title", "diary"]
            csvwriter = csv.DictWriter(csvfile, fieldnames=fieldnames)
            csvwriter.writerow(data)

        data_num += 1
        print(str(data_num) + ' ' + title+": 저장 완료")