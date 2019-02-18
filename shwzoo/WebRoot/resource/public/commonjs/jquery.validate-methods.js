/*!
 * jQuery Validation Plugin v1.14.0
 *
 * http://jqueryvalidation.org/
 *
 */

(function(factory) {
	if (typeof define === "function" && define.amd) {
		define([ "jquery", "./jquery.validate" ], factory);
	} else {
		factory(jQuery);
	}
}
		(function($) {

			(function() {

				function stripHtml(value) {
					// remove html tags and space chars
					return value.replace(/<.[^<>]*?>/g, " ").replace(
							/&nbsp;|&#160;/gi, " ")
					// remove punctuation
					.replace(/[.(),;:!?%#$'\"_+=\/\-“”’]*/g, "");
				}

				$.validator
						.addMethod(
								"maxWords",
								function(value, element, params) {
									return this.optional(element)
											|| stripHtml(value).match(
													/\b\w+\b/g).length <= params;
								},
								$.validator
										.format("Please enter {0} words or less."));

				$.validator
						.addMethod(
								"minWords",
								function(value, element, params) {
									return this.optional(element)
											|| stripHtml(value).match(
													/\b\w+\b/g).length >= params;
								},
								$.validator
										.format("Please enter at least {0} words."));

				$.validator.addMethod("rangeWords", function(value, element,
						params) {
					var valueStripped = stripHtml(value), regex = /\b\w+\b/g;
					return this.optional(element)
							|| valueStripped.match(regex).length >= params[0]
							&& valueStripped.match(regex).length <= params[1];
				}, $.validator
						.format("Please enter between {0} and {1} words."));

			}());

			// Accept a value from a file input based on a required mimetype
			$.validator
					.addMethod(
							"accept",
							function(value, element, param) {
								// Split mime on commas in case we have multiple
								// types we can accept
								var typeParam = typeof param === "string" ? param
										.replace(/\s/g, "").replace(/,/g, "|")
										: "image/*", optionalValue = this
										.optional(element), i, file;

								// Element is optional
								if (optionalValue) {
									return optionalValue;
								}

								if ($(element).attr("type") === "file") {
									// If we are using a wildcard, make it regex
									// friendly
									typeParam = typeParam.replace(/\*/g, ".*");

									// Check if the element has a FileList
									// before checking each file
									if (element.files && element.files.length) {
										for (i = 0; i < element.files.length; i++) {
											file = element.files[i];

											// Grab the mimetype from the loaded
											// file, verify it matches
											if (!file.type.match(new RegExp(
													"\\.?(" + typeParam + ")$",
													"i"))) {
												return false;
											}
										}
									}
								}

								// Either return true because we've validated
								// each file, or because the
								// browser does not support element.files and
								// the FileList feature
								return true;
							},
							$.validator
									.format("Please enter a value with a valid mimetype."));

			$.validator.addMethod("alphanumeric", function(value, element) {
				return this.optional(element) || /^\w+$/i.test(value);
			}, "Letters, numbers, and underscores only please");

			/*
			 * NOTICE: Modified version of
			 * Castle.Components.Validator.CreditCardValidator Redistributed
			 * under the the Apache License 2.0 at
			 * http://www.apache.org/licenses/LICENSE-2.0 Valid Types:
			 * mastercard, visa, amex, dinersclub, enroute, discover, jcb,
			 * unknown, all (overrides all other settings)
			 */
			$.validator.addMethod("creditcardtypes", function(value, element,
					param) {
				if (/[^0-9\-]+/.test(value)) {
					return false;
				}

				value = value.replace(/\D/g, "");

				var validTypes = 0x0000;

				if (param.mastercard) {
					validTypes |= 0x0001;
				}
				if (param.visa) {
					validTypes |= 0x0002;
				}
				if (param.amex) {
					validTypes |= 0x0004;
				}
				if (param.dinersclub) {
					validTypes |= 0x0008;
				}
				if (param.enroute) {
					validTypes |= 0x0010;
				}
				if (param.discover) {
					validTypes |= 0x0020;
				}
				if (param.jcb) {
					validTypes |= 0x0040;
				}
				if (param.unknown) {
					validTypes |= 0x0080;
				}
				if (param.all) {
					validTypes = 0x0001 | 0x0002 | 0x0004 | 0x0008 | 0x0010
							| 0x0020 | 0x0040 | 0x0080;
				}
				if (validTypes & 0x0001 && /^(5[12345])/.test(value)) { // mastercard
					return value.length === 16;
				}
				if (validTypes & 0x0002 && /^(4)/.test(value)) { // visa
					return value.length === 16;
				}
				if (validTypes & 0x0004 && /^(3[47])/.test(value)) { // amex
					return value.length === 15;
				}
				if (validTypes & 0x0008 && /^(3(0[012345]|[68]))/.test(value)) { // dinersclub
					return value.length === 14;
				}
				if (validTypes & 0x0010 && /^(2(014|149))/.test(value)) { // enroute
					return value.length === 15;
				}
				if (validTypes & 0x0020 && /^(6011)/.test(value)) { // discover
					return value.length === 16;
				}
				if (validTypes & 0x0040 && /^(3)/.test(value)) { // jcb
					return value.length === 16;
				}
				if (validTypes & 0x0040 && /^(2131|1800)/.test(value)) { // jcb
					return value.length === 15;
				}
				if (validTypes & 0x0080) { // unknown
					return true;
				}
				return false;
			}, "Please enter a valid credit card number.");

			/**
			 * Validates currencies with any given symbols by
			 * 
			 * @jameslouiz Symbols can be optional or required. Symbols required
			 *             by default
			 * 
			 * Usage examples: currency: ["£", false] - Use false for soft
			 * currency validation currency: ["$", false] currency: ["RM",
			 * false] - also works with text based symbols such as "RM" -
			 * Malaysia Ringgit etc
			 * 
			 * <input class="currencyInput" name="currencyInput">
			 * 
			 * Soft symbol checking currencyInput: { currency: ["$", false] }
			 * 
			 * Strict symbol checking (default) currencyInput: { currency: "$"
			 * //OR currency: ["$", true] }
			 * 
			 * Multiple Symbols currencyInput: { currency: "$,£,¢" }
			 */
			$.validator
					.addMethod(
							"currency",
							function(value, element, param) {
								var isParamString = typeof param === "string", symbol = isParamString ? param
										: param[0], soft = isParamString ? true
										: param[1], regex;

								symbol = symbol.replace(/,/g, "");
								symbol = soft ? symbol + "]" : symbol + "]?";
								regex = "^["
										+ symbol
										+ "([1-9]{1}[0-9]{0,2}(\\,[0-9]{3})*(\\.[0-9]{0,2})?|[1-9]{1}[0-9]{0,}(\\.[0-9]{0,2})?|0(\\.[0-9]{0,2})?|(\\.[0-9]{1,2})?)$";
								regex = new RegExp(regex);
								return this.optional(element)
										|| regex.test(value);

							}, "Please specify a valid currency");

			$.validator
					.addMethod(
							"dateFA",
							function(value, element) {
								return this.optional(element)
										|| /^[1-4]\d{3}\/((0?[1-6]\/((3[0-1])|([1-2][0-9])|(0?[1-9])))|((1[0-2]|(0?[7-9]))\/(30|([1-2][0-9])|(0?[1-9]))))$/
												.test(value);
							}, $.validator.messages.date);

			/**
			 * Return true, if the value is a valid date, also making this
			 * formal check dd/mm/yyyy.
			 * 
			 * @example $.validator.methods.date("01/01/1900")
			 * @result true
			 * 
			 * @example $.validator.methods.date("01/13/1990")
			 * @result false
			 * 
			 * @example $.validator.methods.date("01.01.1900")
			 * @result false
			 * 
			 * @example <input name="pippo" class="{dateITA:true}" />
			 * @desc Declares an optional input element whose value must be a
			 *       valid date.
			 * 
			 * @name $.validator.methods.dateITA
			 * @type Boolean
			 * @cat Plugins/Validate/Methods
			 */
			$.validator
					.addMethod(
							"dateITA",
							function(value, element) {
								var check = false, re = /^\d{1,2}\/\d{1,2}\/\d{4}$/, adata, gg, mm, aaaa, xdata;
								if (re.test(value)) {
									adata = value.split("/");
									gg = parseInt(adata[0], 10);
									mm = parseInt(adata[1], 10);
									aaaa = parseInt(adata[2], 10);
									xdata = new Date(Date.UTC(aaaa, mm - 1, gg,
											12, 0, 0, 0));
									if ((xdata.getUTCFullYear() === aaaa)
											&& (xdata.getUTCMonth() === mm - 1)
											&& (xdata.getUTCDate() === gg)) {
										check = true;
									} else {
										check = false;
									}
								} else {
									check = false;
								}
								return this.optional(element) || check;
							}, $.validator.messages.date);

			$.validator
					.addMethod(
							"dateNL",
							function(value, element) {
								return this.optional(element)
										|| /^(0?[1-9]|[12]\d|3[01])[\.\/\-](0?[1-9]|1[012])[\.\/\-]([12]\d)?(\d\d)$/
												.test(value);
							}, $.validator.messages.date);

			// Older "accept" file extension method. Old docs:
			// http://docs.jquery.com/Plugins/Validation/Methods/accept
			$.validator.addMethod("extension", function(value, element, param) {
				param = typeof param === "string" ? param.replace(/,/g, "|")
						: "png|jpe?g|gif";
				return this.optional(element)
						|| value.match(new RegExp("\\.(" + param + ")$", "i"));
			}, $.validator
					.format("Please enter a value with a valid extension."));

			$.validator.addMethod("integer", function(value, element) {
				return this.optional(element) || /^-?\d+$/.test(value);
			}, "A positive or negative non-decimal number please");

			$.validator
					.addMethod(
							"ipv4",
							function(value, element) {
								return this.optional(element)
										|| /^(25[0-5]|2[0-4]\d|[01]?\d\d?)\.(25[0-5]|2[0-4]\d|[01]?\d\d?)\.(25[0-5]|2[0-4]\d|[01]?\d\d?)\.(25[0-5]|2[0-4]\d|[01]?\d\d?)$/i
												.test(value);
							}, "Please enter a valid IP v4 address.");

			$.validator
					.addMethod(
							"ipv6",
							function(value, element) {
								return this.optional(element)
										|| /^((([0-9A-Fa-f]{1,4}:){7}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}:[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){5}:([0-9A-Fa-f]{1,4}:)?[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){4}:([0-9A-Fa-f]{1,4}:){0,2}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){3}:([0-9A-Fa-f]{1,4}:){0,3}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){2}:([0-9A-Fa-f]{1,4}:){0,4}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}((\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b)\.){3}(\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b))|(([0-9A-Fa-f]{1,4}:){0,5}:((\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b)\.){3}(\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b))|(::([0-9A-Fa-f]{1,4}:){0,5}((\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b)\.){3}(\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b))|([0-9A-Fa-f]{1,4}::([0-9A-Fa-f]{1,4}:){0,5}[0-9A-Fa-f]{1,4})|(::([0-9A-Fa-f]{1,4}:){0,6}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){1,7}:))$/i
												.test(value);
							}, "Please enter a valid IP v6 address.");

			$.validator.addMethod("lettersonly", function(value, element) {
				return this.optional(element) || /^[a-z]+$/i.test(value);
			}, "Letters only please");

			$.validator.addMethod("letterswithbasicpunc", function(value,
					element) {
				return this.optional(element)
						|| /^[a-z\-.,()'"\s]+$/i.test(value);
			}, "Letters or punctuation only please");

			$.validator
					.addMethod(
							"mobileNL",
							function(value, element) {
								return this.optional(element)
										|| /^((\+|00(\s|\s?\-\s?)?)31(\s|\s?\-\s?)?(\(0\)[\-\s]?)?|0)6((\s|\s?\-\s?)?[0-9]){8}$/
												.test(value);
							}, "Please specify a valid mobile number");

			/**
			 * Return true if the field value matches the given format RegExp
			 * 
			 * @example $.validator.methods.pattern("AR1004",element,/^AR\d{4}$/)
			 * @result true
			 * 
			 * @example $.validator.methods.pattern("BR1004",element,/^AR\d{4}$/)
			 * @result false
			 * 
			 * @name $.validator.methods.pattern
			 * @type Boolean
			 * @cat Plugins/Validate/Methods
			 */
			$.validator.addMethod("pattern", function(value, element, param) {
				if (this.optional(element)) {
					return true;
				}
				if (typeof param === "string") {
					param = new RegExp("^(?:" + param + ")$");
				}
				return param.test(value);
			}, "Invalid format.");

			/*
			 * Lets you say "at least X inputs that match selector Y must be
			 * filled."
			 * 
			 * The end result is that neither of these inputs:
			 * 
			 * <input class="productinfo" name="partnumber"> <input
			 * class="productinfo" name="description">
			 * 
			 * ...will validate unless at least one of them is filled.
			 * 
			 * partnumber: {require_from_group: [1,".productinfo"]},
			 * description: {require_from_group: [1,".productinfo"]}
			 * 
			 * options[0]: number of fields that must be filled in the group
			 * options[1]: CSS selector that defines the group of conditionally
			 * required fields
			 */
			$.validator
					.addMethod(
							"require_from_group",
							function(value, element, options) {
								var $fields = $(options[1], element.form), $fieldsFirst = $fields
										.eq(0), validator = $fieldsFirst
										.data("valid_req_grp") ? $fieldsFirst
										.data("valid_req_grp") : $.extend({},
										this), isValid = $fields
										.filter(function() {
											return validator.elementValue(this);
										}).length >= options[0];

								// Store the cloned validator for future
								// validation
								$fieldsFirst.data("valid_req_grp", validator);

								// If element isn't being validated, run each
								// require_from_group field's validation rules
								if (!$(element).data("being_validated")) {
									$fields.data("being_validated", true);
									$fields.each(function() {
										validator.element(this);
									});
									$fields.data("being_validated", false);
								}
								return isValid;
							},
							$.validator
									.format("Please fill at least {0} of these fields."));

			/*
			 * Lets you say "either at least X inputs that match selector Y must
			 * be filled, OR they must all be skipped (left blank)."
			 * 
			 * The end result, is that none of these inputs:
			 * 
			 * <input class="productinfo" name="partnumber"> <input
			 * class="productinfo" name="description"> <input
			 * class="productinfo" name="color">
			 * 
			 * ...will validate unless either at least two of them are filled,
			 * OR none of them are.
			 * 
			 * partnumber: {skip_or_fill_minimum: [2,".productinfo"]},
			 * description: {skip_or_fill_minimum: [2,".productinfo"]}, color:
			 * {skip_or_fill_minimum: [2,".productinfo"]}
			 * 
			 * options[0]: number of fields that must be filled in the group
			 * options[1]: CSS selector that defines the group of conditionally
			 * required fields
			 * 
			 */
			$.validator
					.addMethod(
							"skip_or_fill_minimum",
							function(value, element, options) {
								var $fields = $(options[1], element.form), $fieldsFirst = $fields
										.eq(0), validator = $fieldsFirst
										.data("valid_skip") ? $fieldsFirst
										.data("valid_skip") : $
										.extend({}, this), numberFilled = $fields
										.filter(function() {
											return validator.elementValue(this);
										}).length, isValid = numberFilled === 0
										|| numberFilled >= options[0];

								// Store the cloned validator for future
								// validation
								$fieldsFirst.data("valid_skip", validator);

								// If element isn't being validated, run each
								// skip_or_fill_minimum field's validation rules
								if (!$(element).data("being_validated")) {
									$fields.data("being_validated", true);
									$fields.each(function() {
										validator.element(this);
									});
									$fields.data("being_validated", false);
								}
								return isValid;
							},
							$.validator
									.format("Please either skip these fields or fill at least {0} of them."));

			/*
			 * Validates US States and/or Territories by @jdforsythe Can be case
			 * insensitive or require capitalization - default is case
			 * insensitive Can include US Territories or not - default does not
			 * Can include US Military postal abbreviations (AA, AE, AP) -
			 * default does not
			 * 
			 * Note: "States" always includes DC (District of Colombia)
			 * 
			 * Usage examples:
			 * 
			 * This is the default - case insensitive, no territories, no
			 * military zones stateInput: { caseSensitive: false,
			 * includeTerritories: false, includeMilitary: false }
			 * 
			 * Only allow capital letters, no territories, no military zones
			 * stateInput: { caseSensitive: false }
			 * 
			 * Case insensitive, include territories but not military zones
			 * stateInput: { includeTerritories: true }
			 * 
			 * Only allow capital letters, include territories and military
			 * zones stateInput: { caseSensitive: true, includeTerritories:
			 * true, includeMilitary: true }
			 * 
			 * 
			 * 
			 */

			$.validator
					.addMethod(
							"stateUS",
							function(value, element, options) {
								var isDefault = typeof options === "undefined", caseSensitive = (isDefault || typeof options.caseSensitive === "undefined") ? false
										: options.caseSensitive, includeTerritories = (isDefault || typeof options.includeTerritories === "undefined") ? false
										: options.includeTerritories, includeMilitary = (isDefault || typeof options.includeMilitary === "undefined") ? false
										: options.includeMilitary, regex;

								if (!includeTerritories && !includeMilitary) {
									regex = "^(A[KLRZ]|C[AOT]|D[CE]|FL|GA|HI|I[ADLN]|K[SY]|LA|M[ADEINOST]|N[CDEHJMVY]|O[HKR]|PA|RI|S[CD]|T[NX]|UT|V[AT]|W[AIVY])$";
								} else if (includeTerritories
										&& includeMilitary) {
									regex = "^(A[AEKLPRSZ]|C[AOT]|D[CE]|FL|G[AU]|HI|I[ADLN]|K[SY]|LA|M[ADEINOPST]|N[CDEHJMVY]|O[HKR]|P[AR]|RI|S[CD]|T[NX]|UT|V[AIT]|W[AIVY])$";
								} else if (includeTerritories) {
									regex = "^(A[KLRSZ]|C[AOT]|D[CE]|FL|G[AU]|HI|I[ADLN]|K[SY]|LA|M[ADEINOPST]|N[CDEHJMVY]|O[HKR]|P[AR]|RI|S[CD]|T[NX]|UT|V[AIT]|W[AIVY])$";
								} else {
									regex = "^(A[AEKLPRZ]|C[AOT]|D[CE]|FL|GA|HI|I[ADLN]|K[SY]|LA|M[ADEINOST]|N[CDEHJMVY]|O[HKR]|PA|RI|S[CD]|T[NX]|UT|V[AT]|W[AIVY])$";
								}

								regex = caseSensitive ? new RegExp(regex)
										: new RegExp(regex, "i");
								return this.optional(element)
										|| regex.test(value);
							}, "Please specify a valid state");

			// TODO check if value starts with <, otherwise don't try stripping
			// anything
			$.validator.addMethod("strippedminlength", function(value, element,
					param) {
				return $(value).text().length >= param;
			}, $.validator.format("Please enter at least {0} characters"));

			$.validator.addMethod("time",
					function(value, element) {
						return this.optional(element)
								|| /^([01]\d|2[0-3]|[0-9])(:[0-5]\d){1,2}$/
										.test(value);
					}, "Please enter a valid time, between 00:00 and 23:59");

			$.validator.addMethod("time12h", function(value, element) {
				return this.optional(element)
						|| /^((0?[1-9]|1[012])(:[0-5]\d){1,2}(\ ?[AP]M))$/i
								.test(value);
			}, "Please enter a valid time in 12-hour am/pm format");

			// same as url, but TLD is optional
			$.validator
					.addMethod(
							"url2",
							function(value, element) {
								return this.optional(element)
										|| /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)*(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i
												.test(value);
							}, $.validator.messages.url);

			/**
			 * Return true, if the value is a valid vehicle identification
			 * number (VIN).
			 * 
			 * Works with all kind of text inputs.
			 * 
			 * @example <input type="text" size="20" name="VehicleID"
			 *          class="{required:true,vinUS:true}" />
			 * @desc Declares a required input element whose value must be a
			 *       valid vehicle identification number.
			 * 
			 * @name $.validator.methods.vinUS
			 * @type Boolean
			 * @cat Plugins/Validate/Methods
			 */
			$.validator
					.addMethod(
							"vinUS",
							function(v) {
								if (v.length !== 17) {
									return false;
								}

								var LL = [ "A", "B", "C", "D", "E", "F", "G",
										"H", "J", "K", "L", "M", "N", "P", "R",
										"S", "T", "U", "V", "W", "X", "Y", "Z" ], VL = [
										1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5,
										7, 9, 2, 3, 4, 5, 6, 7, 8, 9 ], FL = [
										8, 7, 6, 5, 4, 3, 2, 10, 0, 9, 8, 7, 6,
										5, 4, 3, 2 ], rs = 0, i, n, d, f, cd, cdv;

								for (i = 0; i < 17; i++) {
									f = FL[i];
									d = v.slice(i, i + 1);
									if (i === 8) {
										cdv = d;
									}
									if (!isNaN(d)) {
										d *= f;
									} else {
										for (n = 0; n < LL.length; n++) {
											if (d.toUpperCase() === LL[n]) {
												d = VL[n];
												d *= f;
												if (isNaN(cdv) && n === 8) {
													cdv = LL[n];
												}
												break;
											}
										}
									}
									rs += d;
								}
								cd = rs % 11;
								if (cd === 10) {
									cd = "X";
								}
								if (cd === cdv) {
									return true;
								}
								return false;
							},
							"The specified vehicle identification number (VIN) is invalid.");

			$.validator.addMethod("zipcodeUS",
					function(value, element) {
						return this.optional(element)
								|| /^\d{5}(-\d{4})?$/.test(value);
					}, "The specified US ZIP Code is invalid");

			$.validator.addMethod("ziprange", function(value, element) {
				return this.optional(element)
						|| /^90[2-5]\d\{2\}-\d{4}$/.test(value);
			}, "Your ZIP-code must be in the range 902xx-xxxx to 905xx-xxxx");

			// --------------------------------------自定义验证方法------------------------------------------------------//
			// 字符验证
			jQuery.validator.addMethod("stringCheck", function(value, element) {
				return this.optional(element)
						|| /^[\u0391-\uFFE5\w]+$/.test(value);
			}, "只能包括中文字、英文字母、数字和下划线");

			// 中文字两个字节
			jQuery.validator.addMethod("byteRangeLength", function(value,
					element, param) {
				var length = value.length;
				for (var i = 0; i < value.length; i++) {
					if (value.charCodeAt(i) > 127) {
						length++;
					}
				}
				return this.optional(element)
						|| (length >= param[0] && length <= param[1]);
			}, "请确保输入的值在4-15个字节之间(一个中文字算2个字节)");

			// 身份证号码验证
			jQuery.validator.addMethod("isIdCardNo", function(value, element) {
				return this.optional(element) || isIdCardNo(value);
			}, "请正确输入您的身份证号码");

			// 手机号码验证
			jQuery.validator.addMethod("isMobile", function(value, element) {
				var length = value.length;
				var mobile = /^0?1[3|4|5|7|8][0-9]\d{8}$/;
				return this.optional(element)
						|| (length == 11 && mobile.test(value));
			}, "请正确填写您的手机号码");

			// 电话号码验证
			jQuery.validator.addMethod("isTel", function(value, element) {
				var tel = /^d{3,4}-?d{7,9}$/; // 电话号码格式010-12345678
				return this.optional(element) || (tel.test(value));
			}, "请正确填写您的电话号码");

			// 联系电话(手机/电话皆可)验证
			jQuery.validator.addMethod("isPhone", function(value, element) {
				var length = value.length;
				var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+d{8})$/;
				var tel = /^d{3,4}-?d{7,9}$/;
				return this.optional(element)
						|| (tel.test(value) || mobile.test(value));

			}, "请正确填写您的联系电话");
			// 密码是纯数字  或者 纯字母 或者二者都有
		    jQuery.validator.addMethod("isNumberOrStr", function (value, element) {
		        var reg = /^[0-9a-zA-Z]{7,16}$/;//纯数字  或者 纯字母 或者二者都有
		        return this.optional(element) || (reg.test(value));
		    }, "请输入纯数字 或者纯字母或者数字和字母的组合,不能输入字符 7-16位之间");
			// 邮政编码验证
			jQuery.validator.addMethod("isZipCode", function(value, element) {
				var tel = /^[0-9]{6}$/;
				return this.optional(element) || (tel.test(value));
			}, "请正确填写您的邮政编码");
			// 下拉选择框必选
			jQuery.validator.addMethod("selectRequired", function(value,
					element) {
				return this.optional(element) || value != -1;
			}, "请选择");
			// 金额验证
			jQuery.validator.addMethod("isMoney",function(value,element){
				var money=/^(([1-9]\d*)|0)(\.\d{0,2})?$/;
				return this.optional(element) || money.test(value);
			},"请正确填写金额");
			// 验证数据库中账户名是否已存在 param存放usertype
			jQuery.validator.addMethod("checkDataRepeat", function(value,
					element, param) {
				var result = -1;
				$.ajax(({
					type : "post",
					url : "/seller/user/checkuserrepeat",
					dataType : "json",
					async : false,
					data : {
						usertype : param[0],
						userkey : value
					},
					success : function(rsl) {
						if (rsl.code == 0) {
							result = 0;
						} else {
						}
					},
					error : function(e) {
					}
				}));
				return this.optional(element) || (result == 0);
			}, "数据库已存在该账户名");
			// 验证数据库中账户名是否已存在 param存放usertype
			jQuery.validator.addMethod("checkMobileRepeat", function(value,
					element, param) {
				var result = -1;
				$.ajax(({
					type : "post",
					url : "/seller/user/checkMobileRepeat",
					dataType : "json",
					async : false,
					data : {
						usertype : param[0],
						userkey : value
					},
					success : function(rsl) {
						if (rsl.code == 0) {
							result = 0;
						} else {
						}
					},
					error : function(e) {
					}
				}));
				return this.optional(element) || (result == 0);
			}, "手机号已存在");

			// 验证数据库中账户名是否已存在 param存放usertype和userid
			jQuery.validator.addMethod("checkAccountRepeat", function(value,
					element, param) {
				var result = -1;
				$.ajax(({
					type : "post",
					url : "/platform/accounts/checkAccountRepeat",
					dataType : "json",
					async : false,
					data : {
						usertype : param[0],
						userid : param[1],
						value : value
					},
					success : function(rsl) {
						if (rsl.Code == 0) {
							result = 0;
						} else {
						}
					},
					error : function(e) {
					}
				}));
				return this.optional(element) || (result == 0);
			}, "数据库已存在该账户名");
			// 验证图片验证码是否正确
			jQuery.validator.addMethod("checkImgCode",
					function(value, element) {
						var result = -1;
						$.ajax(({
							type : "post",
							url : "/seller/user/checkVerification",
							dataType : "json",
							async : false,
							data : {
								imgverification : value
							},
							success : function(rsl) {
								if (rsl.code == 0) {
									result = 0;
								} else {
								}
							},
							error : function(e) {
							}
						}));
						
						return this.optional(element) || (result ==0);
					}, "验证码错误");
			/**
			 * 身份证号码验证
			 */
			function isIdCardNo(num) {

				var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9,
						10, 5, 8, 4, 2, 1);
				var parityBit = new Array("1", "0", "X", "9", "8", "7", "6",
						"5", "4", "3", "2");
				var varArray = new Array();
				var intValue;
				var lngProduct = 0;
				var intCheckDigit;
				var intStrLen = num.length;
				var idNumber = num;
				// initialize
				if ((intStrLen != 15) && (intStrLen != 18)) {
					return false;
				}
				// check and set value
				for (i = 0; i < intStrLen; i++) {
					varArray[i] = idNumber.charAt(i);
					if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
						return false;
					} else if (i < 17) {
						varArray[i] = varArray[i] * factorArr[i];
					}
				}
				if (intStrLen == 18) {
					// check date
					var date8 = idNumber.substring(6, 14);
					if (isDate8(date8) == false) {
						return false;
					}
					// calculate the sum of the products
					for (i = 0; i < 17; i++) {
						lngProduct = lngProduct + varArray[i];
					}
					// calculate the check digit
					intCheckDigit = parityBit[lngProduct % 11];
					// check last digit
					if (varArray[17] != intCheckDigit) {
						return false;
					}
				} else { // length is 15
					// check date
					var date6 = idNumber.substring(6, 12);
					if (isDate6(date6) == false) {
						return false;
					}
				}
				return true;
			}

			function isDate6(sDate) {
				if (!/^[0-9]{6}$/.test(sDate)) {
					return false;
				}
				var year, month, day;
				year = sDate.substring(0, 4);
				month = sDate.substring(4, 6);
				if (year < 1700 || year > 2500)
					return false
				if (month < 1 || month > 12)
					return false
				return true
			}

			function isDate8(sDate) {
				if (!/^[0-9]{8}$/.test(sDate)) {
					return false;
				}
				var year, month, day;
				year = sDate.substring(0, 4);
				month = sDate.substring(4, 6);
				day = sDate.substring(6, 8);
				var iaMonthDays = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30,
						31 ]
				if (year < 1700 || year > 2500)
					return false
				if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0))
					iaMonthDays[1] = 29;
				if (month < 1 || month > 12)
					return false
				if (day < 1 || day > iaMonthDays[month - 1])
					return false
				return true
			}
			// ---------------------------------------------------------------------------------------------------------------------------//

		}));
