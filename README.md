# Assignment

Application: No Problemo

Team Members
Ian Chua Rong Bin	S10179055
Jordan Wong See Siang	S10179230
Andreaz Rozario Goh Jun	S10179869
Han Kai Xiang	S10170577

Application Overview
‘No Problemo’ is an application that has been designed as a tool to increase the convenience of students while they are on campus. For example, at any given time, a student may use the application to see the number of people at a given cafeteria rather than needing to walk all the way there, only to find out that the place is completely occupied. Another example would be that students can use the application to find out the location of their friends so long as they have the application as well, thus making navigation since the campus is rather huge and not everyone may not which block is which. The aforementioned features will increase convenience for students greatly and also helps them reduce the amount of time wasted.

Initial Prototype
Initially, our application was simply going to be a multi-purpose tool to increase the convenience while they were on campus. For example, we wanted to incorporate a page which contained buttons which were linked to important numbers (Reporting purposes, police etc.) which students could press to call immediately. However, after a while, we realised that our application was slowly becoming a myriad of tools with almost no utility which prompted us to rethink our problem statement entirely and the initial solution that we had come up with it. This was when we realised we were so focused on ‘creating convenience’ but we did not actually fully understand what sort of common problems students typically share on campus. As such, we set out to do some research to find out some of the more common inconveniences that students face everyday and worked from there to further develop our solution.
Through our findings, we came to an understanding that one of the biggest problems students face everyday is figuring out where they should eat. The campus has a total of 4 cafeterias to cater to not just students but also all other staff members and as such, they can be extremely packed sometimes. Most of the time, students find themselves walking to one cafeteria only to realise that it is fully packed and needing to walk all the way to another one. This takes up a lot of their time and reduces their lunch breaks to very short ones. Another problem that we discovered was that people waste a lot of time trying to go to different places as the campus is quite huge. There have been many complaints of how annoying it is to navigate through the campus when these students want to find certain blocks/classrooms and their friends. Based on the problems outlined here, we came up with a new application called ‘No Problemo’ and its features will be further explained in the section below.



Features
Start Page	
1.	Existing users will be able to log in to their accounts by tapping on the ‘LOGIN’ Button.

2.	New users may register by tapping on the ‘REGISTER’ Button.

Profile	
1.	Users will be able to see their details which have been retrieved from the Firebase database.

2.	Users can change their profile photo by tapping on the ‘UPLOAD’ button to select an existing photo from their gallery. Alternatively, they may press the ‘TAKE PHOTO’ button to take a brand new photo.

3.	Users will be able to change their passwords upon tapping on the ‘CHANGE PASSWORD’ button whereby they will be redirected to another page to do so.

4.	Upon tapping on the ‘CHANGE MODE’  button, users will be able to toggle between online and offline mode which changes their visibility on the Maps Page. When set to online mode, other users will be able to see their location and when set to offline mode, other users will not be able to see their location.

Food Places	
1.	By tapping on the ‘REFRESH’ button, users will be able to see the number of people at each one of the 4 cafeterias at the current time. The colour of the number will also change depending on how densely packed the cafeteria is. Red indicates that it is extremely packed, yellow indicates that it is relatively packed and green indicates that it is rather empty.

2.	When the user taps on any of these 4 cafeterias, they will be redirected to a map which shows the location of the selected cafeteria location. 

Maps	
1.	On this page, users will be able to see their own location and also the locations of their friends who also have this application

Report	
1.	Users will be able to compose an email within the application which will be sent directly to the reports department. Upon tapping on ‘SEND’, they may select their desired email application to send out their composed email.

Roles and Contributions
Ian	
1. Set up the camera function which allows users to take photos on the spot to be set as their profile photos

2. Set up the upload image function which allows users to upload a photo from their phone’s gallery to be used as their profile photo

3. Created the reporting page which allows users to compose an email to report any incidents

4. Created tabs for each one of the 4 pages within the application

5. Designed a portion of the user interface

Jordan	
1. Pulled Google Maps API to display locations of the various cafeterias and also the user’s current location

2. Created the Maps page to show the location of other users who use the application

3. Designed a portion of the user interface

Andreaz	
1. Retrieved user data from the firebase database and displayed it on the Profile page accordingly.

2. Setup the login function for users to log in to their respective accounts

3. Created the change password page and did the backend code to allow users to change their passwords and also so that the new password will be updated in the database.

4. Designed a portion of the user interface

Kai Xiang	
1. Pulled APIs to display the number of people in each one of the 4 cafeterias.

2. Designed the logo of the application

