# Application Architecture

Broadly, WaveMusic (henceforth referred to as "the app") consists of the main source package and a test package. The test package contains one file for each testable class in the main package. The app follows a three-tier architecture with persistence, business and presentation layers. These layers are represented as collections of classes in the main package, as described in this document.

### Presentation Layer

The presentation layer is implemented through the app's activities. These are the NowPlayingActivity, SongDetailActivity, ArtistActivity, AlbumActivity, PlaylistActivity, PlayQueueActivity, and FileManagementActivity.

*  NowPlayingActivity: This activity displays which song is currently playing as well as a controls to change songs.
*  SongDetailActivity: This activity displays detailed information about the current song including the artist, album and any other metadata available.
*  ArtistActivity: This activity displays information about an artist as well as a list of their songs
*  AlbumActivity: This activity displays information about an album (artist, year etc.) as well as a list of the album's songs.
*  PlaylistActivity: This activity displays the name of a playlist as well as the contained songs.
*  PlayQueueActivity: This activity displays the currently queued songs.
*  FileManagementActivity: This activity displays a file manager for adding/removing music from the library.

### Business Logic Layer

There are several classes defined to handle business logic; they are "presentation layer facing" classes which act as callable interfaces for the presentation layer. This group consists of the PlaybackController, FileManagementController and ViewController. 

*  PlaybackController: The PlaybackController is responsible for implementing interfaces for all necessary playback functionality. This includes starting a song, skipping forwards, skipping backwards, tracking to a specific position, pausing and resuming playback.
*  MusicDirectoryManager: The MusicDirectoryManager provides the functionality to do all necessary reading of music files from the disk. This includes all importing of music from various directories defined by the user.
*  ActivityController: Handles settings and populating the correct activities(s) for the app when a corresponding gesture or click is received.

### Persistence Layer

The persistence layer is defined abstractly by the DatabaseInterface interface, and then implemented by the DatabaseController class. There are also several domain objects; the Song, Playlist, PlaybackQueue and ... classes.



