#!/bin/bash
cd ~/Documents/NCM_Templates_v2/SysOID
#mkdir NCMTemplatesV2
i=1
while [ $i -le 200 ]; do
	echo "fetching for template $i"
	curl 'http://localhost:8060/client/api/json/ncmsettings/allSysOID?DEVICETEMPLATEID='$i -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:94.0) Gecko/20100101 Firefox/94.0' -H 'Accept: application/json, text/javascript, */*; q=0.01' -H 'Accept-Language: en-US,en;q=0.5' --compressed -H 'X-ZCSRF-TOKEN: opmcsrftoken=38533274bfff55547562c30740ce025246dfdda46e8f9da13ef943aa4939afdd28ab5c2e9b3fb3588c910a64949319f48515dae8ae90e239b05bcbca454d8b12' -H 'X-Requested-With: XMLHttpRequest' -H 'Connection: keep-alive' -H 'Referer: http://localhost:8060/apiclient/ember/index.jsp' -H 'Cookie: CountryName=INDIA; encryptPassForAutomaticSignin=6797405b801ab775e098648a3d5810e3aa194c1b4bc78697; userNameForAutomaticSignin=admin; domainNameForAutomaticSignin=Authenticator; signInAutomatically=true; authrule_name=Authenticator; WBGNXN90gr=OlLG7kJw52; isiframeenabled=true; w4E2UR3IaU=qsq3CSrZ3z; JSESSIONID=254C31E695D56E35B544B9180C5EF5C4; NFA__SSO=EE01D48236B7DBB58C5BBC6DCF831657; opmcsrfcookie=38533274bfff55547562c30740ce025246dfdda46e8f9da13ef943aa4939afdd28ab5c2e9b3fb3588c910a64949319f48515dae8ae90e239b05bcbca454d8b12' -H 'Sec-Fetch-Dest: empty' -H 'Sec-Fetch-Mode: cors' -H 'Sec-Fetch-Site: same-origin' > $i.txt
	i=`expr $i + 1`
done
