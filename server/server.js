const express = require('express');
const app = express();
const router = require('./routers/router');

app.use(express.json({limit: '50mb'}));
app.use(express.urlencoded({limit: '50mb'}));

router(app);

const PORT = process.env.PORT;

app.listen(PORT, () => console.log(`Server is running on port ${PORT}`));

