# Application Architecture

Broadly, Wave Music (henceforth referred to as "the app") consists of the main source package and a test package. The test package contains one file for each testable class in the main package. The app follows a three-tier architecture with persistence, logic and presentation layers. These layers are represented as collections of classes in the main package, as described in this document.

## Presentation Layer

The presentation layer is implemented through the app's activities. All of these activities are responsible for displaying information to the user in some form. All activities are listed below:

* **NowPlayingActivity:** Displays which song is currently playing as well as the controls to modify the playback of the song (pause, resume, skip).
* **SongDetailActivity:** Displays detailed information about the current song including the artist, album and any other metadata available.
* **ArtistActivity:**  Displays information about an artist as well as a list of their songs.
* **AlbumActivity:**  Displays information about an album (artist, year etc.) as well as a list of the album's songs.
* **PlaylistActivity:**  Displays the name of a playlist as well as a list of the songs it contains.
* **PlayQueueActivity:**  Displays the currently queued for playback.
* **FileManagementActivity:**  Displays a file manager for adding music from the device to the library.

## Logic Layer

There are several classes defined to handle business logic; they are "presentation layer facing" classes which act as callable interfaces for the presentation layer. This group consists of the following classes:

* **PlaybackController:** Responsible for implementing interfaces for all necessary playback functionality. This includes starting a song, skipping forwards, skipping backwards, tracking to a specific position, pausing and resuming playback.
* **MusicDirectoryManager:** Provides the functionality to do all necessary reading of music files from the disk. This includes all importing of music from various directories defined by the user.
* **ActivityController:** Handles settings and populating the correct activities(s) for the app when a corresponding action occurs that requires a new activity.
* **PermissionManager:** Responsible for setting the correct system permissions to allow access to the device's file structure.
* **SampleAssetManager:** Responsible for reading the sample music files from the project's assets folder when the app launches.

## Persistence Layer

The persistence layer is defined abstractly by the `IDatabaseController` interface, and then implemented by the `DatabaseStub` class.

* **DatabaseStub:** Responsible for storing the library of songs as well as querying the library of songs with a variety of different search parameters (song name, artist name, album name, etc.).

## Domain Specific Objects

There are also several domain objects that are used throughout the hierarchy.

* **Song:** An object for each song in the library. It contains information about the song such as the song's name, artist, album, and location on disk.
* **Playlist:** A user-created playlist that contains a list of songs belonging to the playlist.
* **PlaybackQueue:** A list of upcoming songs to be played. The contents of this list depends on which group of songs was selected to be played. For example, a whole artist, album, or playlist.
