const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp({
    credential: admin.credential.applicationDefault(),
});

const firestore = admin.firestore();

const runtimeOptions = {
    timeoutSeconds: 512,
}

exports.updateFrameTrends = functions.runWith(runtimeOptions).https.onCall(async (data, context) => {
    functions.logger.log("Frame Trends :::", data.documentPath);

    firestore.doc(data.documentPath)
        .update({
            frameTrend: data.frameTrend
        })

});