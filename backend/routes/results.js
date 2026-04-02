const express = require('express');
const router = express.Router();
const jwt = require('jsonwebtoken');
const Result = require('../models/Result');

const SECRET = 'facedetection_secret_key';

// Middleware to verify token
function authMiddleware(req, res, next) {
  const token = req.headers['authorization'];
  if (!token) return res.status(401).json({ message: 'No token' });
  try {
    const decoded = jwt.verify(token, SECRET);
    req.userId = decoded.userId;
    next();
  } catch {
    res.status(401).json({ message: 'Invalid token' });
  }
}

// Save result
router.post('/save', authMiddleware, async (req, res) => {
  try {
    const { image, smile, leftEye, rightEye } = req.body;
    const result = new Result({
      userId: req.userId,
      image,
      smile,
      leftEye,
      rightEye
    });
    await result.save();
    res.json({ message: 'Result saved!' });
  } catch (err) {
    res.status(500).json({ message: 'Save failed' });
  }
});

// Get history
router.get('/history', authMiddleware, async (req, res) => {
  try {
    const results = await Result.find({ userId: req.userId }).sort({ createdAt: -1 });
    res.json(results);
  } catch (err) {
    res.status(500).json({ message: 'Fetch failed' });
  }
});

module.exports = router;