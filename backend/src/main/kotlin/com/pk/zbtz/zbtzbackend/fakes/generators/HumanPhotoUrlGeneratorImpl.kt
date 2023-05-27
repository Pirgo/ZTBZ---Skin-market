package com.pk.zbtz.zbtzbackend.fakes.generators

import org.springframework.stereotype.Component

@Component
class HumanPhotoUrlGeneratorImpl : HumanPhotoUrlGenerator {
    override fun generate(): String =
        humanPhotoUrls.random()

    private companion object {
        val humanPhotoUrls = listOf(
            "https://image.tmdb.org/t/p/w500/pVHspL9QWsXCm3t3VXEaCJ9y8Zz.jpg",
            "https://image.tmdb.org/t/p/w500/usR5NspRlWgXKUe9qVsCtTCpe4l.jpg",
            "https://image.tmdb.org/t/p/w500/vL805BcPV6xbu2HUZig8ZEvnjJL.jpg",
            "https://image.tmdb.org/t/p/w500/mPQkVbV1SBo0d7lE2BlEivCOrs2.jpg",
            "https://image.tmdb.org/t/p/w500/3Tqh4AMtqCqPljNe2agZD9ZeoFf.jpg",
            "https://image.tmdb.org//t/p/w500/zDedofqLD0OQtRVbxr04KPytr2R.jpg",
            "https://image.tmdb.org/t/p/w500/zwSv5uXzPTtmitFe39UdqnVwmdL.jpg",
            "https://image.tmdb.org/t/p/w500/4D0PpNI0kmP58hgrwGC3wCjxhnm.jpg",
            "https://image.tmdb.org/t/p/w500/4iA9QztvUnhf3YS2U0Z3lieTknc.jpg",
            "https://image.tmdb.org/t/p/w500/182lEPNJDKIJITUlz8M5KbCI0rK.jpg",
            "https://image.tmdb.org/t/p/w500/eMdXRtJVvescfdxHAINVf7dcpAV.jpg",
            "https://image.tmdb.org/t/p/w500/58czHc8QU1L4H0N45T0UML8efgS.jpg",
            "https://image.tmdb.org/t/p/w500/jrvW5B0M15ZwIuriv0lsgocqC5x.jpg",
            "https://image.tmdb.org/t/p/w500/gnleHTtPw9k2PwTncNWH7hKh21m.jpg",
            "https://image.tmdb.org/t/p/w500/vFA9YTXdgQ5VCkPzbdQfBC4tQrG.jpg",
            "https://image.tmdb.org/t/p/w500/ckXk2zfLOO0xRIaxd0YBvS7x2ZP.jpg",
            "https://image.tmdb.org/t/p/w500/3FIaN13otjAYGSYZ4LJKmEYvKEa.jpg",
            "https://image.tmdb.org/t/p/w500/rVJRXGHjDSWkC58SgBYfMbeAm1t.jpg",
        )
    }
}