goog.module('holder');

const jre = goog.require('jre');

/** @define {string} */
const value = goog.define('holder.value', 'unknown');

/** @define {string} */
const testEscape = goog.define('holder.testEscape', 'false');

jre.addSystemPropertyFromGoogDefine('holder.value', value);
jre.addSystemPropertyFromGoogDefine('holder.testEscape', testEscape);

exports = {
    value,
    testEscape,
};