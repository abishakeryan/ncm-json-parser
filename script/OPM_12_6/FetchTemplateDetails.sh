#!/bin/bash
#create a folder and navigate to that folder in the below cd command
cd /home/local/ZOHOCORP/abishake-9966/Documents/NCM_Template_Latest_OPM/Template_Details
i=1
while [ $i -le 210 ]; do
	curl -k --compressed 'https://s24x7-nw-u2.csez.zohocorpin.com:8060/client/api/json/ncmsettings/getDeviceTemplateDetails?DEVICETEMPLATEID='$i -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:104.0) Gecko/20100101 Firefox/104.0' -H 'Accept: application/json, text/javascript, */*; q=0.01' -H 'Accept-Language: en-US,en;q=0.5' -H 'Accept-Encoding: gzip, deflate, br' -H 'X-ZCSRF-TOKEN: opmcsrftoken=e294164865a8863f04a47851d7e96a751628a912e6096793d1f6d0f5f3622719f84528fb741af89b7cf7df06d0202663373f4ace377ebad3ad15df5dbab36932' -H 'OPMCurrentRoute: https%3A%2F%2Fs24x7-nw-u2.csez.zohocorpin.com%3A8060%2Fapiclient%2Fember%2Findex.jsp%23%2FSettings%2FNcmConfig%2FDeviceTemplatesEdit' -H 'X-Requested-With: XMLHttpRequest' -H 'Connection: keep-alive' -H 'Referer: https://s24x7-nw-u2.csez.zohocorpin.com:8060/apiclient/ember/index.jsp' -H 'Cookie: _cseziamadt=8d637a27e9c3c0271762bbde4d6ca01d93e5e75691fc9c2d3eb80c8e2e03b45c93f94f226b34997e745835caaefe0b82ce27adb435b3f71c48a4634ab308ad6b; _cseziambdt=36f27bb9752085a59a6a6d7b6fdd38383671dc94f69d79f7bd6f09a846df061ac8c29d77a3f1780bc106741d1daae3dfea481bb8ba17ce26a4ba6267633fb20d; JSESSIONID=3A70C2D0735A009C6B3803CA6820317A; opmcsrfcookie=e294164865a8863f04a47851d7e96a751628a912e6096793d1f6d0f5f3622719f84528fb741af89b7cf7df06d0202663373f4ace377ebad3ad15df5dbab36932; _zcsr_tmp=e294164865a8863f04a47851d7e96a751628a912e6096793d1f6d0f5f3622719f84528fb741af89b7cf7df06d0202663373f4ace377ebad3ad15df5dbab36932; isiframeenabled=true; CountryName=INDIA; signInAutomatically=true; NFA__SSO=A395D97CB970097D8B77948FACD1308F' -H 'Sec-Fetch-Dest: empty' -H 'Sec-Fetch-Mode: cors' -H 'Sec-Fetch-Site: same-origin' -H 'Pragma: no-cache' -H 'Cache-Control: no-cache' > $i.txt
	i=`expr $i + 1`
done
