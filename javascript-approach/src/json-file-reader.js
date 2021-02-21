const fs = require("fs");

const convertJsonToObject = (path) => JSON.parse(fs.readFileSync(path, "utf-8"));

module.exports = { convertJsonToObject };
