# Osnovna pravila za zastitu koda
-keep class com.example.radioapp.data.** { *; }
-keep class com.example.radioapp.databinding.** { *; }

# KRITICNO ZA STABILNOST MUZIKE:
# Android sistem koristi refleksiju da bi nasao tvoj servis.
# Ako mu R8 promeni ime, aplikacija puca pri pokretanju pesme.
-keep class com.example.radioapp.service.PlaybackService { *; }
-keep class androidx.media3.session.MediaSessionService { *; }
-keep class androidx.media3.session.MediaSession { *; }
-keepattributes Signature, InnerClasses, EnclosingMethod, *Annotation*

# Zastita za mrezni klijent (OkHttp)
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**
