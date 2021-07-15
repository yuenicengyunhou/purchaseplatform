保存所有版本的apk包。

##      加固后签名命令
####    *****\jdk8u291\bin>
####    jarsigner -verbose -keystore {jks文件路径} -signedjar {生成签名后的apk文件路径} {未签名apk文件路径} {jks别名}
####    jarsigner -verbose -keystore D:\Develop\WorkSpace\purchase-android-app\app\purchase.jks -signedjar D:\Develop\WorkSpace\purchase-android-app\app\app-release-signed_pro_1.0.35.apk D:\Develop\WorkSpace\purchase-android-app\app\app-release-unsigned_pro_1.0.35.apk purchase
