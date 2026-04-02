const mongoose = require('mongoose');

const resultSchema = new mongoose.Schema({
  userId: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
  image: { type: String },
  smile: { type: Number },
  leftEye: { type: Number },
  rightEye: { type: Number },
  createdAt: { type: Date, default: Date.now }
});

module.exports = mongoose.model('Result', resultSchema);