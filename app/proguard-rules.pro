# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile



#---------------------------------基本指令区----------------------------------
-optimizationpasses 5 # 代码混淆压缩比，在0~7之间，默认为5
-dontskipnonpubliclibraryclassmembers # 指定不去忽略非公共库的类
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/* # 混淆时所采用的算法
-keepattributes *Annotation*,InnerClasses #保留Annotation不混淆
-keepattributes Signature #避免混淆泛型
-keepattributes SourceFile,LineNumberTable #抛出异常时保留代码行号
#-ignorewarnings

-keepattributes EnclosingMethod
-dontpreverify # 不做预校验,加快混淆速度
#----------------------------------------------------------------------------
