curl \
-F "status=2" \
-F "notify=0" \
-F "ipa=@app/build/outputs/apk/ci/app-ci.apk" \
-H "X-HockeyAppToken: $HOCKEY_APP_TOKEN" \
https://rink.hockeyapp.net/api/2/apps/$HOCKEY_APP_ID/app_versions/upload