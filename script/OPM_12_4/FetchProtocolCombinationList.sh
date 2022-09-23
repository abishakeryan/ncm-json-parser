#!/bin/bash
cd ~/Documents/NCM_Templates_v2/ProtocolCombinations
#mkdir NCMTemplatesV2
i=1
while [ $i -le 200 ]; do
	curl 'http://localhost:8060/client/api/json/admin/SubmitQuery' -X POST -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:94.0) Gecko/20100101 Firefox/94.0' -H 'Accept: */*' -H 'Accept-Language: en-US,en;q=0.5' --compressed -H 'Content-Type: application/x-www-form-urlencoded; charset=UTF-8' -H 'X-ZCSRF-TOKEN: opmcsrftoken=ddfa8f775ec3814bbf0dbf096cb83c4586fe209d300bc231f34690213d85083e510203f4ab62356aac4c417302d4ee5495d979ccc6c661029a0f4fb1c6fafe93' -H 'X-Requested-With: XMLHttpRequest' -H 'Origin: http://localhost:8060' -H 'Connection: keep-alive' -H 'Referer: http://localhost:8060/apiclient/fluidicv2/dbQuery.jsp' -H 'Cookie: CountryName=INDIA; encryptPassForAutomaticSignin=6797405b801ab775e098648a3d5810e3aa194c1b4bc78697; userNameForAutomaticSignin=admin; domainNameForAutomaticSignin=Authenticator; signInAutomatically=true; authrule_name=Authenticator; WBGNXN90gr=OlLG7kJw52; JSESSIONID=B935FECCAFB5325E0436B4E400B277BF; isiframeenabled=true; w4E2UR3IaU=qsq3CSrZ3z; NFA__SSO=4BC14CFABEDC5BB004D7C2EA0C10D6D1; opmcsrfcookie=ddfa8f775ec3814bbf0dbf096cb83c4586fe209d300bc231f34690213d85083e510203f4ab62356aac4c417302d4ee5495d979ccc6c661029a0f4fb1c6fafe93' -H 'Sec-Fetch-Dest: empty' -H 'Sec-Fetch-Mode: cors' -H 'Sec-Fetch-Site: same-origin' --data-raw 'query=select+*+from+NCMPROTOCOLCOMBINATION+where+devicetemplateid+%3D+'$i > $i.txt
	i=`expr $i + 1`
done
