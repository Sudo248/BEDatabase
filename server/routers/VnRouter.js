const express = require('express');

const router = express.Router();

const vnController = require('../controller/VnController');

router
    .route('/')
    .put(vnController.postVns)
    .post(vnController.postVns);

router.route('/:id').delete(vnController.deleteVn);

module.exports = router;