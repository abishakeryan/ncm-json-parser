#!/bin/bash
cd ~/Documents/NCM_Templates_v2/TemplatesDetails
#mkdir NCMTemplatesV2
i=1
while [ $i -le 200 ]; do
	curl 'http://s24x7-nw-c1:8060/client/api/json/ncmsettings/getDeviceTemplateDetails?DEVICETEMPLATEID='$i -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:94.0) Gecko/20100101 Firefox/94.0' -H 'Accept: application/json, text/javascript, */*; q=0.01' -H 'Accept-Language: en-US,en;q=0.5' --compressed -H 'X-ZCSRF-TOKEN: opmcsrftoken=9184db12ed9a1ae2daa50fc42caddb2421b196515e405e40750ec0cc5c81b03d6f88998ed23c768d51f30ec55feb4bda0383694e4aeab414e48c53ca4bb010b1' -H 'OPMCurrentRoute: http%3A%2F%2Fs24x7-nw-c1%3A8060%2Fapiclient%2Fember%2Findex.jsp%23%2FSettings%2FNcmConfig%2FViewDeviceTemplate%2FConfigure' -H 'X-Requested-With: XMLHttpRequest' -H 'Connection: keep-alive' -H 'Referer: http://s24x7-nw-c1:8060/apiclient/ember/index.jsp' -H 'Cookie: CountryName=INDIA; encryptPassForAutomaticSignin=6797405b801ab775e098648a3d5810e3aa194c1b4bc78697; userNameForAutomaticSignin=admin; domainNameForAutomaticSignin=Authenticator; signInAutomatically=true; authrule_name=Authenticator; igDxJqBBi2=RAV2GOQnL1; RrphWaEnAr=XxoVYlus1N; Y7VsAu0NX1=ZPR45DWbxS; oeTyiq2793=9YHREzdzFj; Lu3TjFoC4D=3sb62pbs14; JSESSIONID=420ED6C09001F57286EBF7905ABF4714; isiframeenabled=true; NFA__SSO=9E80940A2FB762B5FE2422E5E77EC919; opmcsrfcookie=9184db12ed9a1ae2daa50fc42caddb2421b196515e405e40750ec0cc5c81b03d6f88998ed23c768d51f30ec55feb4bda0383694e4aeab414e48c53ca4bb010b1' > $i.txt
	i=`expr $i + 1`
done
