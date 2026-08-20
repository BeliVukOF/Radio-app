package com.example.radioapp.repository

import com.example.radioapp.data.RadioStation

object StationRepository {
    val allStations = listOf(
        // Albania
        RadioStation("Radio Tirana 1", "http://79.106.48.2:8000/radiotirana1", "Albania"),
        RadioStation("Top Albanian Radio", "https://live.top-media.al/tar", "Albania"),
        RadioStation("My Music Radio", "https://live.top-media.al/mmr", "Albania"),
        RadioStation("Radio Club FM", "https://cp1.sednastream.com/proxy/clubfm?mp=/stream", "Albania"),

        // Bosnia
        RadioStation("Kolubara", "https://kolubara.name.ba:8000/", "Bosnia"),
        RadioStation("Dzungla 1", "https://cast2.name.ba/proxy/dzungla/stream", "Bosnia"),
        RadioStation("Dzungla 2", "https://cast2.name.ba/proxy/dzungla2/stream", "Bosnia"),
        RadioStation("Dzungla 3", "https://cast2.name.ba/proxy/dzungla3/stream", "Bosnia"),

        // Bulgaria
        RadioStation("BG Veselina", "https://bss1.neterra.tv/veselina/veselina.m3u8", "Bulgaria"),
        RadioStation("BG Energy", "https://play.global.audio:8000/nrj128", "Bulgaria"),

        // Croatia
        RadioStation("Banovina Light", "https://audio.radio-banovina.hr:7008/stream", "Croatia"),
        RadioStation("Banovina Turbo", "https://audio.radio-banovina.hr:7010/stream", "Croatia"),
        RadioStation("Banovina", "https://audio.radio-banovina.hr:7000/stream", "Croatia"),
        RadioStation("Extra Fm", "https://relay2.social3.hr/radio/8190/radio.mp3", "Croatia"),
        RadioStation("Extra Fm Throwback", "https://relay2.social3.hr/radio/8170/radio.mp3", "Croatia"),
        RadioStation("Extra Fm Fresh", "https://relay2.social3.hr/radio/8360/radio.mp3", "Croatia"),
        RadioStation("Laganini FM", "http://cast5.asurahosting.com/proxy/soundset/stream", "Croatia"),
        RadioStation("Enter Zagreb", "http://live.enterzagreb.hr:8023/stream", "Croatia"),
        RadioStation("Otvoreni Live", "https://stream.otvoreni.hr/otvoreni", "Croatia"),
        RadioStation("Otvoreni Chill", "https://stream.otvoreni.hr/chill", "Croatia"),

        // Greece
        RadioStation("Plus Radio", "https://eco.onestreaming.com/proxy/plusradio/stream", "Greece"),
        RadioStation("RadioBoss", "http://168.119.74.185:8150/live", "Greece"),
        RadioStation("Trelown", "http://i2.streams.ovh:7109/stream", "Greece"),

        // Macedonia
        RadioStation("STIL Radio", "https://eu4.fastcast4u.com/proxy/stilradiomk?mp=/1", "Macedonia"),
        RadioStation("Kanal 103", "http://radiostream.neotel.mk/kanal103", "Macedonia"),
        RadioStation("Antenna 5", "http://antenna5stream.neotel.mk:8000/live128", "Macedonia"),
        RadioStation("Radio Maria MK", "http://dreamsiteradiocp.com:8118/stream", "Macedonia"),
        RadioStation("Just Music", "https://radiostream.neotel.mk/justmusicradio", "Macedonia"),
        RadioStation("Life Radio", "http://liferadio.ddns.net:8000/", "Macedonia"),
        RadioStation("Urban FM", "https://securestreams4.autopo.st:1668/stream", "Macedonia"),
        RadioStation("City Radio Skopje", "http://176.9.117.123:9998/live", "Macedonia"),
        RadioStation("MRT Radio 1", "https://vod-c57.interspace.com/channel_abr/47/playlist.m3u8", "Macedonia"),
        RadioStation("MRT Radio 2", "https://vod-c57.interspace.com/channel_abr/48/playlist.m3u8", "Macedonia"),
        RadioStation("MRT Radio 3", "https://vod-c57.interspace.com/channel_abr/49/playlist.m3u8", "Macedonia"),

        // Montenegro
        RadioStation("Radio Svetigora", "http://svetigoralive.com:8879/;", "Montenegro"),
        RadioStation("Antena M", "http://radioservis.me:8010/antenamlive", "Montenegro"),
        RadioStation("Reggaeneracija", "http://5.189.179.77:8000/stream", "Montenegro"),
        RadioStation("Radio S1", "https://stream.radios.rs:9008/;", "Montenegro"),
        RadioStation("Radio Elmag", "https://radio.elmag.me/poprock.mp3", "Montenegro"),
        RadioStation("Radio Berane", "https://stream.iradio.pro/proxy/radioberane?mp=/stream;", "Montenegro"),

        // Serbia
        RadioStation("Ritam Srca", "https://s4.radio.co/s0f65dad2d/listen", "Serbia"),
        RadioStation("Lola", "http://streaming.tdiradio.com/radiolola.mp3", "Serbia"),
        RadioStation("Play", "http://stream.playradio.rs:8001/play.mp3", "Serbia"),
        RadioStation("TDI", "http://streaming.tdiradio.com:8000/tdiradio.mp3", "Serbia"),
        RadioStation("Hit FM", "https://streaming.hitfm.rs/hit.mp3", "Serbia"),
        RadioStation("Radio In", "https://radio3-64ssl.streaming.rs:9212/;*.mp3", "Serbia"),
        RadioStation("Radio S ExYu", "https://stream.radios.rs:9042/;*.mp3", "Serbia"),
        RadioStation("Radio S 1", "https://stream.radios.rs:9000/radios1.mp3", "Serbia"),
        RadioStation("Radio S 2", "https://stream.radios.rs:9002/radios2.mp3", "Serbia"),
        RadioStation("Radio S 3", "https://stream.radios.rs:9004/radios3.mp3", "Serbia"),
        RadioStation("Radio S 4", "https://stream.radios.rs:9006/radios4.mp3", "Serbia"),
        RadioStation("Radio Jat", "https://streaming.radiojat.rs/radiojat.mp3", "Serbia"),
        RadioStation("Nostalgie", "https://nostalgie128ssl.streaming.rs:9252/;*.mp3", "Serbia"),
        RadioStation("Pink Rock", "https://edge9.pink.rs/rockstream", "Serbia"),
        RadioStation("Cool Radio", "https://live.coolradio.rs/cool128", "Serbia"),
        RadioStation("Karolina", "https://streaming.karolina.rs/karolina.mp3", "Serbia"),
        RadioStation("Radio Morava", "https://e3.radiomorava.rs/radio/8000/radiomorava128.mp3", "Serbia"),
        RadioStation("Naxi FM", "https://naxi128.streaming.rs:9152/;*.mp3", "Serbia")
    )
}