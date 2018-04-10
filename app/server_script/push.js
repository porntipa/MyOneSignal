var OneSignal = require('onesignal-node');

// first we need to create a client
var myClient = new OneSignal.Client({
    userAuthKey: 'ZjJhYjhkYmMtNDljOS00YzlkLTk3ZTMtNmZlYmM0ZTljZWQ3',
    app: { appAuthKey: 'ZWY1NTE2M2YtYmJmNy00NGExLWE1ZmMtZjMzNDU5ODE4NDg3', appId: 'db3505d4-aa23-421b-bfcc-0caa9279b16a' }
});

// we need to create a notification to send
var firstNotification = new OneSignal.Notification({
    contents: {
        en: "CodeMobiles.com",
        th: "โค้ดโมบายส์ จำกัด"
    }
});

// set target users
firstNotification.setIncludedSegments(['All']);
firstNotification.setExcludedSegments(['Inactive Users']);

// set notification parameters
firstNotification.setParameter('data', {"abc": "123", "foo": "bar"});
//firstNotification.setParameter('send_after', 'Thu Sep 24 2015 14:00:00 GMT-0700 (PDT)');

// send this notification to All Users except Inactive ones
myClient.sendNotification(firstNotification, function (err, httpResponse,data) {
   if (err) {
       console.log('Something went wrong...');
   } else {
       console.log(data, httpResponse.statusCode);
   }
});