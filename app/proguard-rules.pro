# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-dontwarn org.slf4j.**

-keep class fr.paug.androidmakers.model.** { *; }

-keep class com.google.ar.** { *; }
-keep class com.google.vr.** { *; }

-dontwarn com.google.ar.**