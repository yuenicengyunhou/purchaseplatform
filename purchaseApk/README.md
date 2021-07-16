保存所有版本的apk包。


##      签名命令 v1
####    **\jdk8u291\bin>
####    jarsigner -verbose -keystore {jks文件路径} -signedjar {生成签名后的apk文件路径} {未签名apk文件路径} {jks别名}
####    jarsigner -verbose -keystore D:\**\purchase.jks -signedjar D:\**\output.apk D:\**\input.apk purchase


##      签名命令 v2
###     先进行zip对齐
####    **\AndroidSDK\build-tools\30.0.3>
####    .\zipalign.exe -v -p 4 {zip对齐前的apk} {zip对齐后的apk}
####    .\zipalign.exe -v -p 4 input.apk output.apk
###     再签名
####    **\AndroidSDK\build-tools\30.0.3\lib>
####    java -jar apksigner.jar sign --ks {jks文件路径} --ks-key-alias {jks别名} --ks-pass pass:{keystore密码} --key-pass pass:{key密码} --out {签名后的apk文件路径} {签名前的apk文件路径}
####    java -jar apksigner.jar sign --ks D:\**\purchase.jks --ks-key-alias purchase --ks-pass pass:android --key-pass pass:android --out D:\**\output.apk D:\**\input.apk