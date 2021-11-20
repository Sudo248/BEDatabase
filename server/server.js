const express = require('express');
const app = express();
const router = require('./routers/router');

app.use(express.json());

router(app);

const PORT = process.env.PORT;

app.listen(PORT, () => console.log(`Server is running on port ${PORT}`));

