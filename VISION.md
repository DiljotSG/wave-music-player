# Wave Music Player

## Table of Contents

- 1 - [Documentation for Iteration 0](README.md/#documentation-for-iteration-0)
  - 1.1 - [Application Vision Statement](README.md/#application-vision-statement)
  - 1.2 - [Application Features](README.md/#application-features)
  - 1.3 - [Iteration 1 user stories](README.md/#iteration-1-user-stories)
- 2 - [Documentation for Iteration 1](README.md/#documentation-for-iteration-1)
  - 2.1 - [Brief Description - 1](README.md#brief-description-1)
  - 2.2 - [Application Architecture Document](README.md/#application-architecture-document)
  - 2.3 - [Contribution Guidelines](README.md/#contribution-guidelines)
  - 2.4 - [User stories moved to Iteration 2](README.md#user-stories-moved-to-iteration-2)
- 3 - [Documentation for Iteration 2](README.md/#documentation-for-iteration-2)
  - 3.1 - [Brief Description - 2](README.md#brief-description-2)
  - 3.2 - [Implementation](README.md#implementation)
  - 3.3 - [Known Issues](README.md#known-issues)

## Documentation for Iteration 0

### Application Vision Statement

The Wave music player will allow users to playback and search for music that is stored on their mobile device. The application will focus on usability and simplicity by offering several core features including music playback, control, searching and sorting, and playlist creation.

Users will be able to manage and listen to music that is imported from their device. Songs can be played in their original order, shuffled order, or on repeat. Specific music can be found by searching for individual songs or by filtering based on song properties such as title, artist, or album. Music can be organized into user-created playlists which can then be exported if desired. Additionally, graphical themes will be available to allow users to personalize their Wave experience.

Wave will be developed for a target audience ranging from teenagers to young adults who want a simpler way of managing and listening to their music. To reflect this demographic, the application will feature a sleek and intuitive interface. Users that have a large or diverse music library are also part of the target audience. To accommodate these users, the application will provide multiple ways of sorting and searching for music, an important feature for music fanatics.

The users of Wave will find that the application provides a large improvement over other complicated and confusing music management tools. With a focus on simplicity, new users will be able to easily learn the functionality of the application and search for music within their library quickly. In addition to a focus on learnability, the application will provide users with a way to forward their favourite tracks to others by allowing them to export their music, either as a list of songs contained in a playlist or their entire song library. Lastly, users can customize the visual theme of the application to better suit their personal preferences.

The application will be considered successful when the following criteria are met. The first metric we are interested in is the stability of the application, especially during demanding tasks such as scrolling, searching, and sorting large music libraries. The stability of the application will be measured by how often the user’s application crashes during 12-hour usage periods. This criteria will be considered a success if the user experiences less than one crash per 12-hour usage periods. Lastly, the application will be considered successful if it remains installed on a user’s device for four months. To ensure that this metric is meaningful, a goal of an average usage of 30 minutes per day throughout the four-month retention period will be set.

### Application Features

The application has several features.

They are created as "issues" on the GitLab project.

**[View the list of application features here](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues?label_name%5B%5D=Feature)**.

Features for the *entire* application:

1. [Shuffle the music playback order](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues/18)
2. [Viewing the properties of a song](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues/17)
3. [Manage custom playlists](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues/16)
4. [Add and remove songs from the library](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues/15)
5. [Changing the app theme](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues/14)
6. [Share a list of music from the user's library](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues/13)
7. [Manage song notes](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues/12)
8. ["Liking" a song](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues/10)
9. [Searching for music](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues/9)
10. [Sorting music](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues/8)
11. [Music controls](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues/7)

### Iteration 1 user stories

**[View the list of user stories for Iteration 1 here](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues?label_name=User+Story&milestone_title=Iteration+1&state=all)**.

User stories that *will be implemented* in **iteration 1**:

1. [Viewing the artist for a song](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues/43)
2. [Viewing the album for a song](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues/41)
3. [Viewing a song's name](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues/40)
4. [Choose the folders that contain music](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues/37)
5. [Resume a paused song](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues/26)
6. [Skipping a song](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues/24)
7. [Pausing a song](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues/22)
8. [Selecting a song to play](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues/19)

## Documentation for Iteration 1

### Brief Description - 1

Upon starting the Wave Music Player Application, the user is presented with their music located in Android's default Music directory. The app will automatically scan this directory for songs, and display them to the user in the `MainActivity` for playback. There are several playback controls implemented in this iteration. The buttons and the are descriptions are listed below.

| Symbol | Meaning                                              |
|--------|------------------------------------------------------|
| `<<`   | Skipping back to the previous track.                 |
| `<`    | Restarting the current track.                        |
| `[]`   | Pausing the current track (must be playing).         |
| `>`    | Playing/resuming the current track (must be paused). |
| `>>`   | Skipping to the next track.                          |

The application comes packaged with four sample tracks, these tracks will be extracted to Android's default Music directory upon initial launch of the application. The songs are then read from the Music directory for the user to play. The app has two main views, the `MainActivity` and `NowPlayingActivity`, both are described in more detail in the Application Architecture document.

### Application Architecture Document

**[View the application architecture document here](ARCHITECTURE.md)**.

### Contribution Guidelines

**[View the repository contribution guidelines here](CONTRIBUTING.md)**.

### User stories moved to Iteration 2

The following user stories were moved to the next iteration:

1. [Choose the folders that contain music](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues/37) (from Iteration 1).

## Documentation for Iteration 2

### Brief Description - 2

In iteration 2 of the Wave Music Player application, the team's primary focus was building the HSQL database for the application. Our app evolved in several ways this iteration. We now allow the user to browse their music library in several different ways, one of which is with categories. On launch, the app presents the user with five categories, each of which are listed below. The second way we allow the user to browse their library is by offering a search view. The search view allows the user to search by song name, artist or album.

Categories:

- **My Library:** Allows the user to view all songs in their library.
- **Playlists:** Allows the user to manage their playlists, including creating new ones, and adding/removing songs from existing playlists, and removing playlists.
- **Artists:** Allows the user to browse their entire library by Artist.
- **Albums:** Allows the user to browse their entire library by Album.
- **Liked Songs:** Allows the user to browse the music they have liked in their library.

### Implementation

This iteration also offers the user a NowPlayingView that includes a volume control slider and a like/unlike button. The behaviour of the like button is implemented, however, the button does not change to signify the change in liked song state. Additionally, it is important to note that the default behaviour of the application is to *shuffle* all music in the current view sometimes leading to the same track being played consecutively (as the included library is tiny).

### Known Issues

- When a song is allowed to play to its end and the next song begins playing, the title text of the activity is not updated with the now playing song's title.

### Incomplete Features

The following features were not completed this iteration due to the user stories listed below that were not assigned for this iteration. The assigned user stories [Choose the folders that contain music](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues/37) from Iteration 1 and [Add currently playing song to a playlist](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues/23) from iteration 2 were not completed this iteration and will be moved to iteration 3.

- Sort Music
  - [Sort by most played songs](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues/28)
- Music Controls
  - [Repeat a song](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues/20)
  - [Fade between songs](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues/21)
  - [Add currently playing song to a playlist](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues/23)
  - [Choose playback location in song](https://code.cs.umanitoba.ca/comp3350-summer2019/team-ten-10/issues/62)
