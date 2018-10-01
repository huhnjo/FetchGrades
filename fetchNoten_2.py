import requests
import re
import sys
from bs4 import BeautifulSoup


username = sys.argv[1]
password = sys.argv[2]
ilias_url = "https://cas.uni-mannheim.de/cas/login?service=https%3A%2F%2Filias.uni-mannheim.de%2Filias.php%3FbaseClass%3DilPersonalDesktopGUI%26cmd%3DjumpToSelectedItems"

s = requests.Session()

def get_request():
    get_re = requests.get(ilias_url)
    cookies = get_re.cookies
    lt = re.findall("(LT-.*?)\"", get_re.text)[0]
    return [lt, cookies]


def post_request():
    get_request_list = get_request()
    lt = get_request_list[0]
    cookies = get_request_list[1]
    payload = {
        'username': username,
        'password': password,
        'lt': lt,
        'execution': 'e1s1',
        '_eventId': 'submit',
        'submit': 'Anmelden'
    }
    response = s.post(ilias_url, data=payload, cookies=cookies)
    return response


def getASI():
    ''' urlForAsi = 'https://portal.uni-mannheim.de/qisserveriframe/rds?state=user&type=0&topitem=pruefungen&language=de' '''
    '''response = s.get(urlForAsi)'''
    response = s.get("https://cas.uni-mannheim.de/cas/login?service=https%3A%2F%2Fportal.uni-mannheim.de%2Fqisserveriframe%2Frds%3Fstate%3Duser%26type%3D1%26topitem%3Dpruefungen%26language%3Dde")
    return response


def login():
    portal2 = 'https://cas.uni-mannheim.de/cas/login?service=https%3A%2F%2Fportal2.uni-mannheim.de/portal2/rds%3Fstate%3Duser%26type%3D1'
    response = s.get(portal2)
    return response


def fetchNotenPage(rep):
    soup = BeautifulSoup(rep.text, 'html.parser')
    list = soup.findAll('a')
    i = 0
    for item in list:
        '''print(item)'''
        if i == 2:
            link = item.__getitem__('href')
        i += 1

    rep = s.get(link)
    '''print(rep.text)'''
    return rep

def parseNoten(content):
    soup = BeautifulSoup(content.text, 'html.parser')
    i = 0
    array = []

    innerlist = []
    k = 0
    for item in soup.find_all('td', {'class': 'posrecords'}):
        if ((i % 11) == 0) & (i != 0):
            array.append(innerlist)
            innerlist = []
            k = 0
        parsed = item.text.replace(u'\xa0', u' ')
        parsed = re.sub('\s+', ' ', parsed)
        parsed = re.sub('<!-- document.write\(Math.round', ' ', parsed)
        parsed = re.sub('; //--> ', ' ', parsed)
        parsed = re.sub('\*10\)/10\)', ' ', parsed)
        parsed = re.sub('\(', ' ', parsed)
        innerlist.append(parsed)
        i += 1
        k += 1
    return array

response = post_request()
login()
rep = getASI()
notenpage = fetchNotenPage(rep)
array = parseNoten(notenpage)
print(array)


