interface Attachment {
    val type: String
}

class AttachmentPhoto(override val type: String = "Photo") : Attachment {
    val photos = emptyArray<Photo>()
}

class Photo(override val type: String) : Attachment {
    val id: Int
        get() = TODO()
    val albumId: Int
        get() = TODO()
    val ownerId: Int
        get() = TODO()
    val userId: Int
        get() = TODO()
    val text: String
        get() = TODO()
    val date: Int
        get() = TODO()
}

class AttachmentAudio(override val type: String = "Audio") : Attachment {
    val audios = emptyArray<Audio>()
}

class Audio(override val type: String) : Attachment {
    val id: Int
        get() = TODO()
    val ownerId: Int
        get() = TODO()
    val artist: String
        get() = TODO()
    val title: String
        get() = TODO()
    val duration: Int
        get() = TODO()
    val url: String
        get() = TODO()
}

class AttachmentVideo(override val type: String = "Video") : Attachment {
    val videos = emptyArray<Video>()
}

class Video(override val type: String) : Attachment {
    val id: Int
        get() = TODO()
    val ownerId: Int
        get() = TODO()
    val title: String
        get() = TODO()
    val description: String
        get() = TODO()
    val duration: Int
        get() = TODO()
}

class AttachmentFile(override val type: String = "File") : Attachment {
    val files = emptyArray<File>()
}

class File(override val type: String) : Attachment {
    val id: Int
        get() = TODO()
    val ownerId: Int
        get() = TODO()
    val title: String
        get() = TODO()
    val size: Int
        get() = TODO()
    val ext: String
        get() = TODO()
}

class AttachmentSticker(override val type: String = "Sticker") : Attachment {
    val stickers = emptyArray<Sticker>()
}

class Sticker(override val type: String) : Attachment {
    val productId: Int
        get() = TODO()
    val stickerId: Int
        get() = TODO()
    val animationUrl: String
        get() = TODO()
    val isAllowed: Boolean
        get() = TODO()
}