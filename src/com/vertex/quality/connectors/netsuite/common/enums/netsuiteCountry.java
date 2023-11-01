package com.vertex.quality.connectors.netsuite.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum netsuiteCountry
{

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/Ascension_Island">Ascension Island</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#AC">AC</a>, ASC, -1,
//		 * Exceptionally reserved]
//		 */
//		AC("Ascension Island", "ASC", -1),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Andorra">Andorra</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#AD">AD</a>, AND, 16,
		 * Officially assigned]
		 */
		AD("Andorra", "AND", 20),

		/**
		 * <a href="http://en.wikipedia.org/wiki/United_Arab_Emirates">United Arab Emirates</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#AE">AE</a>, AE, 784,
		 * Officially assigned]
		 */
		AE("United Arab Emirates", "ARE", 784),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Afghanistan">Afghanistan</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#AF">AF</a>, AFG, 4,
		 * Officially assigned]
		 */
		AF("Afghanistan", "AFG", 4),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Antigua_and_Barbuda">Antigua and Barbuda</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#AG">AG</a>, ATG, 28,
		 * Officially assigned]
		 */
		AG("Antigua and Barbuda", "ATG", 28),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Anguilla">Anguilla</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#AI">AI</a>, AIA, 660,
		 * Officially assigned]
		 */
		AI("Anguilla", "AIA", 660),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Albania">Albania</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#AL">AL</a>, ALB, 8,
		 * Officially assigned]
		 */
		AL("Albania", "ALB", 8),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Armenia">Armenia</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#AM">AM</a>, ARM, 51,
		 * Officially assigned]
		 */
		AM("Armenia", "ARM", 51),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/Netherlands_Antilles">Netherlands Antilles</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#AN">AN</a>, ANT, 530,
//		 * Traditionally reserved]
//		 *
//		 * <p>
//		 * Since version 1.16, the value of alpha-3 code of this entry is {@code ANT}
//		 * (not <code><a href="http://en.wikipedia.org/wiki/ISO_3166-3#ANHH">ANHH</a></code>).
//		 * </p>
//		 */
//		AN("Netherlands Antilles", "ANT", 530),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Angola">Angola</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#AO">AO</a>, AGO, 24,
		 * Officially assigned]
		 */
		AO("Angola", "AGO", 24),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Antarctica">Antarctica</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#AQ">AQ</a>, ATA, 10,
		 * Officially assigned]
		 */
		AQ("Antarctica", "ATA", 10),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Argentina">Argentina</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#AR">AR</a>, ARG, 32,
		 * Officially assigned]
		 */
		AR("Argentina", "ARG", 32),

		/**
		 * <a href="http://en.wikipedia.org/wiki/American_Samoa">American Samoa</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#AS">AS</a>, ASM, 16,
		 * Officially assigned]
		 */
		AS("American Samoa", "ASM", 16),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Austria">Austria</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#AT">AT</a>, AUT, 40,
		 * Officially assigned]
		 */
		AT("Austria", "AUT", 40),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Australia">Australia</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#AU">AU</a>, AUS, 36,
		 * Officially assigned]
		 */
		AU("Australia", "AUS", 36),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Aruba">Aruba</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#AW">AW</a>, ABW, 533,
		 * Officially assigned]
		 */
		AW("Aruba", "ABW", 533),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Azerbaijan">Azerbaijan</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#AZ">AZ</a>, AZE, 31,
		 * Officially assigned]
		 */
		AZ("Azerbaijan", "AZE", 31),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Bosnia_and_Herzegovina">Bosnia and Herzegovina</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#BA">BA</a>, BIH, 70,
		 * Officially assigned]
		 */
		BA("Bosnia and Herzegovina", "BIH", 70),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Barbados">Barbados</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#BB">BB</a>, BRB, 52,
		 * Officially assigned]
		 */
		BB("Barbados", "BRB", 52),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Bangladesh">Bangladesh</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#BD">BD</a>, BGD, 50,
		 * Officially assigned]
		 */
		BD("Bangladesh", "BGD", 50),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Belgium">Belgium</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#BE">BE</a>, BEL, 56,
		 * Officially assigned]
		 */
		BE("Belgium", "BEL", 56),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Burkina_Faso">Burkina Faso</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#BF">BF</a>, BFA, 854,
		 * Officially assigned]
		 */
		BF("Burkina Faso", "BFA", 854),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Bulgaria">Bulgaria</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#BG">BG</a>, BGR, 100,
		 * Officially assigned]
		 */
		BG("Bulgaria", "BGR", 100),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Bahrain">Bahrain</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#BH">BH</a>, BHR, 48,
		 * Officially assigned]
		 */
		BH("Bahrain", "BHR", 48),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Burundi">Burundi</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#BI">BI</a>, BDI, 108,
		 * Officially assigned]
		 */
		BI("Burundi", "BDI", 108),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Benin">Benin</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#BJ">BJ</a>, BEN, 204,
		 * Officially assigned]
		 */
		BJ("Benin", "BEN", 204),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Saint_Barth%C3%A9lemy">Saint Barth&eacute;lemy</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#BL">BL</a>, BLM, 652,
		 * Officially assigned]
		 */
		BL("Saint Barthélemy", "BLM", 652),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Bermuda">Bermuda</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#BM">BM</a>, BMU, 60,
		 * Officially assigned]
		 */
		BM("Bermuda", "BMU", 60),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Brunei">Brunei Darussalam</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#BN">BN</a>, BRN, 96,
		 * Officially assigned]
		 */
		BN("Brunei Darussalam", "BRN", 96),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Bolivia">Bolivia, Plurinational State of</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#BO">BO</a>, BOL, 68,
		 * Officially assigned]
		 */
		BO("Bolivia", "BOL", 68),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Caribbean_Netherlands">Bonaire, Saint Eustatius and Saba</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#BQ">BQ</a>, BES, 535,
		 * Officially assigned]
		 */
		BQ("Bonaire, Saint Eustatius and Saba", "BES", 535),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Brazil">Brazil</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#BR">BR</a>, BRA, 76,
		 * Officially assigned]
		 */
		BR("Brazil", "BRA", 76),

		/**
		 * <a href="http://en.wikipedia.org/wiki/The_Bahamas">Bahamas</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#BS">BS</a>, BHS, 44,
		 * Officially assigned]
		 */
		BS("Bahamas", "BHS", 44),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Bhutan">Bhutan</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#BT">BT</a>, BTN, 64,
		 * Officially assigned]
		 */
		BT("Bhutan", "BTN", 64),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/Burma">Burma</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#BU">BU</a>, BUMM, 104,
//		 * Officially assigned]
//		 *
//		 * <p>
//		 * Since version 1.16, the value of alpha-3 code of this entry is {@code BUR}
//		 * (not <code><a href="http://en.wikipedia.org/wiki/ISO_3166-3#BUMM">BUMM</a></code>).
//		 * </p>
//		 *
//		 */
//		BU("Burma", "BUR", 104),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Bouvet_Island">Bouvet Island</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#BV">BV</a>, BVT, 74,
		 * Officially assigned]
		 */
		BV("Bouvet Island", "BVT", 74),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Botswana">Botswana</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#BW">BW</a>, BWA, 72,
		 * Officially assigned]
		 */
		BW("Botswana", "BWA", 72),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Belarus">Belarus</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#BY">BY</a>, BLR, 112,
		 * Officially assigned]
		 */
		BY("Belarus", "BLR", 112),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Belize">Belize</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#BZ">BZ</a>, BLZ, 84,
		 * Officially assigned]
		 */
		BZ("Belize", "BLZ", 84),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Canada">Canada</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#CA">CA</a>, CAN, 124,
		 * Officially assigned]
		 */
		CA("Canada", "CAN", 124),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/Cocos_(Keeling)_Islands">Cocos (Keeling) Islands</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#CC">CC</a>, CCK, 166,
//		 * Officially assigned]
//		 */
//		CC("Cocos (Keeling) Islands", "CCK", 166),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/Democratic_Republic_of_the_Congo">Congo, the Democratic Republic of the</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#CD">CD</a>, COD, 180,
//		 * Officially assigned]
//		 *
//		 * @see #ZR
//		 */
//		CD("Congo, the Democratic Republic of the", "COD", 180),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Central_African_Republic">Central African Republic</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#CF">CF</a>, CAF, 140,
		 * Officially assigned]
		 */
		CF("Central African Republic", "CAF", 140),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Switzerland">Switzerland</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#CH">CH</a>, CHE, 756,
		 * Officially assigned]
		 */
		CH("Switzerland", "CHE", 756),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/C%C3%B4te_d%27Ivoire">C&ocirc;te d'Ivoire</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#CI">CI</a>, CIV, 384,
//		 * Officially assigned]
//		 */
//		CI("Cote d'Ivoire", "CIV", 384),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Cook_Islands">Cook Islands</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#CK">CK</a>, COK, 184,
		 * Officially assigned]
		 */
		CK("Cook Islands", "COK", 184),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Chile">Chile</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#CL">CL</a>, CHL, 152,
		 * Officially assigned]
		 */
		CL("Chile", "CHL", 152),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Cameroon">Cameroon</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#CM">CM</a>, CMR, 120,
		 * Officially assigned]
		 */
		CM("Cameroon", "CMR", 120),

		/**
		 * <a href="http://en.wikipedia.org/wiki/China">China</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#CN">CN</a>, CHN, 156,
		 * Officially assigned]
		 */
		CN("China", "CHN", 156),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Colombia">Colombia</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#CO">CO</a>, COL, 170,
		 * Officially assigned]
		 */
		CO("Colombia", "COL", 170),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/Clipperton_Island">Clipperton Island</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#CP">CP</a>, CPT, -1,
//		 * Exceptionally reserved]
//		 */
//		CP("Clipperton Island", "CPT", -1),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Costa_Rica">Costa Rica</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#CR">CR</a>, CRI, 188,
		 * Officially assigned]
		 */
		CR("Costa Rica", "CRI", 188),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Serbia_and_Montenegro">Serbia and Montenegro</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#CS">CS</a>, SCG, 891,
		 * Traditionally reserved]
		 *
		 * <p>
		 * Since version 1.16, the value of alpha-3 code of this entry is {@code SCG}
		 * (not <code><a href="http://en.wikipedia.org/wiki/ISO_3166-3#CSXX">CSXX</a></code>).
		 * </p>
		 */
		CS("Montenegro", "SCG", 891),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Cuba">Cuba</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#CU">CU</a>, CUB, 192,
		 * Officially assigned]
		 */
		CU("Cuba", "CUB", 192),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Cape_Verde">Cape Verde</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#CV">CV</a>, CPV, 132,
		 * Officially assigned]
		 */
		CV("Cape Verde", "CPV", 132),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Christmas_Island">Christmas Island</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#CX">CX</a>, CXR, 162,
		 * Officially assigned]
		 */
		CX("Christmas Island", "CXR", 162),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Cyprus">Cyprus</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#CY">CY</a>, CYP, 196,
		 * Officially assigned]
		 */
		CY("Cyprus", "CYP", 196),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Czech_Republic">Czech Republic</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#CZ">CZ</a>, CZE, 203,
		 * Officially assigned]
		 */
		CZ("Czech Republic", "CZE", 203),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Germany">Germany</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#DE">DE</a>, DEU, 276,
		 * Officially assigned]
		 */
		DE("Germany", "DEU", 276),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/Diego_Garcia">Diego Garcia</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#DG">DG</a>, DGA, -1,
//		 * Exceptionally reserved]
//		 */
//		DG("Diego Garcia", "DGA", -1),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Djibouti">Djibouti</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#DJ">DJ</a>, DJI, 262,
		 * Officially assigned]
		 */
		DJ("Djibouti", "DJI", 262),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Denmark">Denmark</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#DK">DK</a>, DNK, 208,
		 * Officially assigned]
		 */
		DK("Denmark", "DNK", 208),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Dominica">Dominica</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#DM">DM</a>, DMA, 212,
		 * Officially assigned]
		 */
		DM("Dominica", "DMA", 212),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Dominican_Republic">Dominican Republic</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#DO">DO</a>, DOM, 214,
		 * Officially assigned]
		 */
		DO("Dominican Republic", "DOM", 214),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Algeria">Algeria</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#DZ">DZ</a>, DZA, 12,
		 * Officially assigned]
		 */
		DZ("Algeria", "DZA", 12),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/Ceuta">Ceuta</a>,
//		 * <a href="http://en.wikipedia.org/wiki/Melilla">Melilla</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#EA">EA</a>, null, -1,
//		 * Exceptionally reserved]
//		 */
//		EA("Ceuta, Melilla", null, -1),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Ecuador">Ecuador</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#EC">EC</a>, ECU, 218,
		 * Officially assigned]
		 */
		EC("Ecuador", "ECU", 218),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Estonia">Estonia</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#EE">EE</a>, EST, 233,
		 * Officially assigned]
		 */
		EE("Estonia", "EST", 233),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Egypt">Egypt</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#EG">EG</a>, EGY, 818,
		 * Officially assigned]
		 */
		EG("Egypt", "EGY", 818),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Western_Sahara">Western Sahara</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#EH">EH</a>, ESH, 732,
		 * Officially assigned]
		 */
		EH("Western Sahara", "ESH", 732),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Eritrea">Eritrea</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#ER">ER</a>, ERI, 232,
		 * Officially assigned]
		 */
		ER("Eritrea", "ERI", 232),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Spain">Spain</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#ES">ES</a>, ESP, 724,
		 * Officially assigned]
		 */
		ES("Spain", "ESP", 724),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Ethiopia">Ethiopia</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#ET">ET</a>, ETH, 231,
		 * Officially assigned]
		 */
		ET("Ethiopia", "ETH", 231),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/European_Union">European Union</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#EU">EU</a>, null, -1,
//		 * Exceptionally reserved]
//		 */
//		EU("European Union", null, -1),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/Eurozone">Eurozone</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#EZ">EZ</a>, null, -1,
//		 * Exceptionally reserved]
//		 *
//		 * @since 1.23
//		 */
//		EZ("Eurozone", null, -1),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Finland">Finland</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#FI">FI</a>, FIN, 246,
		 * Officially assigned]
		 *
		 * @see #SF
		 */
		FI("Finland", "FIN", 246),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Fiji">Fiji</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#">FJ</a>, FJI, 242,
		 * Officially assigned]
		 */
		FJ("Fiji", "FJI", 242),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Falkland_Islands">Falkland Islands (Malvinas)</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#FK">FK</a>, FLK, 238,
		 * Officially assigned]
		 */
		FK("Falkland Islands", "FLK", 238),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Federated_States_of_Micronesia">Micronesia, Federated States of</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#FM">FM</a>, FSM, 583,
		 * Officially assigned]
		 */
		FM("Micronesia, Federal State of", "FSM", 583),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Faroe_Islands">Faroe Islands</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#FO">FO</a>, FRO, 234,
		 * Officially assigned]
		 */
		FO("Faroe Islands", "FRO", 234),

		/**
		 * <a href="http://en.wikipedia.org/wiki/France">France</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#FR">FR</a>, FRA, 250,
		 * Officially assigned]
		 */
		FR("France", "FRA", 250),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/Metropolitan_France">France, Metropolitan</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#FX">FX</a>, FXX, 249,
//		 * Exceptionally reserved]
//		 *
//		 * <p>
//		 * Since version 1.17, the numeric code of this entry is 249.
//		 * </p>
//		 */
//		FX("France, Metropolitan", "FXX", 249),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Gabon">Gabon </a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#GA">GA</a>, GAB, 266,
		 * Officially assigned]
		 */
		GA("Gabon", "GAB", 266),

		/**
		 * <a href="http://en.wikipedia.org/wiki/United_Kingdom">United Kingdom</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#GB">GB</a>, GBR, 826,
		 * Officially assigned]
		 *
		 * @see #UK
		 */
		GB("United Kingdom", "GBR", 826),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Grenada">Grenada</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#GD">GD</a>, GRD, 308,
		 * Officially assigned]
		 */
		GD("Grenada", "GRD", 308),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Georgia_(country)">Georgia</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#GE">GE</a>, GEO, 268,
		 * Officially assigned]
		 */
		GE("Georgia", "GEO", 268),

		/**
		 * <a href="http://en.wikipedia.org/wiki/French_Guiana">French Guiana</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#GF">GF</a>, GUF, 254,
		 * Officially assigned]
		 */
		GF("French Guiana", "GUF", 254),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Guernsey">Guernsey</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#GG">GG</a>, GGY, 831,
		 * Officially assigned]
		 */
		GG("Guernsey", "GGY", 831),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Ghana">Ghana</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#GH">GH</a>, GHA, 288,
		 * Officially assigned]
		 */
		GH("Ghana", "GHA", 288),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Gibraltar">Gibraltar</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#GI">GI</a>, GIB, 292,
		 * Officially assigned]
		 */
		GI("Gibraltar", "GIB", 292),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Greenland">Greenland</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#GL">GL</a>, GRL, 304,
		 * Officially assigned]
		 */
		GL("Greenland", "GRL", 304),

		/**
		 * <a href="http://en.wikipedia.org/wiki/The_Gambia">Gambia</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#GM">GM</a>, GMB, 270,
		 * Officially assigned]
		 */
		GM("Gambia", "GMB", 270),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Guinea">Guinea</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#GN">GN</a>, GIN, 324,
		 * Officially assigned]
		 */
		GN("Guinea", "GIN", 324),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Guadeloupe">Guadeloupe</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#GP">GP</a>, GLP, 312,
		 * Officially assigned]
		 */
		GP("Guadeloupe", "GLP", 312),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Equatorial_Guinea">Equatorial Guinea</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#GQ">GQ</a>, GNQ, 226,
		 * Officially assigned]
		 */
		GQ("Equatorial Guinea", "GNQ", 226),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Greece">Greece</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#GR">GR</a>, GRC, 300,
		 * Officially assigned]
		 */
		GR("Greece", "GRC", 300),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Guatemala">Guatemala</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#GT">GT</a>, GTM, 320,
		 * Officially assigned]
		 */
		GT("Guatemala", "GTM", 320),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Guam">Guam</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#GU">GU</a>, GUM, 316,
		 * Officially assigned]
		 */
		GU("Guam", "GUM", 316),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Guinea-Bissau">Guinea-Bissau</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#GW">GW</a>, GNB, 624,
		 * Officially assigned]
		 */
		GW("Guinea-Bissau", "GNB", 624),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Guyana">Guyana</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#GY">GY</a>, GUY, 328,
		 * Officially assigned]
		 */
		GY("Guyana", "GUY", 328),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Hong_Kong">Hong Kong</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#HK">HK</a>, HKG, 344,
		 * Officially assigned]
		 */
		HK("Hong Kong", "HKG", 344),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Honduras">Honduras</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#HN">HN</a>, HND, 340,
		 * Officially assigned]
		 */
		HN("Honduras", "HND", 340),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Croatia">Croatia</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#HR">HR</a>, HRV, 191,
		 * Officially assigned]
		 */
		HR("Croatia/Hrvatska", "HRV", 191),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Haiti">Haiti</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#HT">HT</a>, HTI, 332,
		 * Officially assigned]
		 */
		HT("Haiti", "HTI", 332),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Hungary">Hungary</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#HU">HU</a>, HUN, 348,
		 * Officially assigned]
		 */
		HU("Hungary", "HUN", 348),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Canary_Islands">Canary Islands</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#IC">IC</a>, null, -1,
		 * Exceptionally reserved]
		 */
		IC("Canary Islands", null, -1),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Indonesia">Indonesia</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#ID">ID</a>, IDN, 360,
		 * Officially assigned]
		 */
		ID("Indonesia", "IDN", 360),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Republic_of_Ireland">Ireland</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#IE">IE</a>, IRL, 372,
		 * Officially assigned]
		 */
		IE("Ireland", "IRL", 372),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Israel">Israel</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#IL">IL</a>, ISR, 376,
		 * Officially assigned]
		 */
		IL("Israel", "ISR", 376),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Isle_of_Man">Isle of Man</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#IM">IM</a>, IMN, 833,
		 * Officially assigned]
		 */
		IM("Isle of Man", "IMN", 833),

		/**
		 * <a href="http://en.wikipedia.org/wiki/India">India</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#IN">IN</a>, IND, 356,
		 * Officially assigned]
		 */
		IN("India", "IND", 356),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/British_Indian_Ocean_Territory">British Indian Ocean Territory</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#IO">IO</a>, IOT, 86,
//		 * Officially assigned]
//		 */
//		IO("British Indian Ocean Territory", "IOT", 86),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Iraq">Iraq</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#IQ">IQ</a>, IRQ, 368,
		 * Officially assigned]
		 */
		IQ("Iraq", "IRQ", 368),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Iran">Iran, Islamic Republic of</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#IR">IR</a>, IRN, 364,
		 * Officially assigned]
		 */
		IR("Iran (Islamic Republic of)", "IRN", 364),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Iceland">Iceland</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#IS">IS</a>, ISL, 352,
		 * Officially assigned]
		 */
		IS("Iceland", "ISL", 352),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Italy">Italy</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#IT">IT</a>, ITA, 380,
		 * Officially assigned]
		 */
		IT("Italy", "ITA", 380),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Jersey">Jersey</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#JE">JE</a>, JEY, 832,
		 * Officially assigned]
		 */
		JE("Jersey", "JEY", 832),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Jamaica">Jamaica</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#JM">JM</a>, JAM, 388,
		 * Officially assigned]
		 */
		JM("Jamaica", "JAM", 388),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Jordan">Jordan</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#JO">JO</a>, JOR, 400,
		 * Officially assigned]
		 */
		JO("Jordan", "JOR", 400),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Japan">Japan</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#JP">JP</a>, JPN, 392,
		 * Officially assigned]
		 */
		JP("Japan", "JPN", 392),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Kenya">Kenya</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#KE">KE</a>, KEN, 404,
		 * Officially assigned]
		 */
		KE("Kenya", "KEN", 404),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Kyrgyzstan">Kyrgyzstan</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#KG">KG</a>, KGZ, 417,
		 * Officially assigned]
		 */
		KG("Kyrgyzstan", "KGZ", 417),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Cambodia">Cambodia</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#KH">KH</a>, KHM, 116,
		 * Officially assigned]
		 */
		KH("Cambodia", "KHM", 116),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Kiribati">Kiribati</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#KI">KI</a>, KIR, 296,
		 * Officially assigned]
		 */
		KI("Kiribati", "KIR", 296),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Comoros">Comoros</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#KM">KM</a>, COM, 174,
		 * Officially assigned]
		 */
		KM("Comoros", "COM", 174),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Saint_Kitts_and_Nevis">Saint Kitts and Nevis</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#KN">KN</a>, KNA, 659,
		 * Officially assigned]
		 */
		KN("Saint Kitts and Nevis", "KNA", 659),

		/**
		 * <a href="http://en.wikipedia.org/wiki/South_Korea">Korea, Republic of</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#KR">KR</a>, KOR, 410,
		 * Officially assigned]
		 */
		KR("Korea, Republic of", "KOR", 410),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Kuwait">Kuwait</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#KW">KW</a>, KWT, 414,
		 * Officially assigned]
		 */
		KW("Kuwait", "KWT", 414),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Cayman_Islands">Cayman Islands</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#KY">KY</a>, CYM, 136,
		 * Officially assigned]
		 */
		KY("Cayman Islands", "CYM", 136),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Kazakhstan">Kazakhstan</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#KZ">KZ</a>, KAZ, 398,
		 * Officially assigned]
		 */
		KZ("Kazakhstan", "KAZ", 398),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Laos">Lao People's Democratic Republic</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#LA">LA</a>, LAO, 418,
		 * Officially assigned]
		 */
		LA("Lao People's Democratic Republic", "LAO", 418),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Lebanon">Lebanon</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#LB">LB</a>, LBN, 422,
		 * Officially assigned]
		 */
		LB("Lebanon", "LBN", 422),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Saint_Lucia">Saint Lucia</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#LC">LC</a>, LCA, 662,
		 * Officially assigned]
		 */
		LC("Saint Lucia", "LCA", 662),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Liechtenstein">Liechtenstein</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#LI">LI</a>, LIE, 438,
		 * Officially assigned]
		 */
		LI("Liechtenstein", "LIE", 438),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Sri_Lanka">Sri Lanka</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#LK">LK</a>, LKA, 144,
		 * Officially assigned]
		 */
		LK("Sri Lanka", "LKA", 144),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Liberia">Liberia</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#LR">LR</a>, LBR, 430,
		 * Officially assigned]
		 */
		LR("Liberia", "LBR", 430),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Lesotho">Lesotho</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#LS">LS</a>, LSO, 426,
		 * Officially assigned]
		 */
		LS("Lesotho", "LSO", 426),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Lithuania">Lithuania</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#LT">LT</a>, LTU, 440,
		 * Officially assigned]
		 */
		LT("Lithuania", "LTU", 440),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Luxembourg">Luxembourg</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#LU">LU</a>, LUX, 442,
		 * Officially assigned]
		 */
		LU("Luxembourg", "LUX", 442),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Latvia">Latvia</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#LV">LV</a>, LVA, 428,
		 * Officially assigned]
		 */
		LV("Latvia", "LVA", 428),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Libya">Libya</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#LY">LY</a>, LBY, 434,
		 * Officially assigned]
		 */
		LY("Libya", "LBY", 434),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Morocco">Morocco</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#MA">MA</a>, MAR, 504,
		 * Officially assigned]
		 */
		MA("Morocco", "MAR", 504),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Monaco">Monaco</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#MC">MC</a>, MCO, 492,
		 * Officially assigned]
		 */
		MC("Monaco", "MCO", 492),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Moldova">Moldova, Republic of</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#MD">MD</a>, MDA, 498,
		 * Officially assigned]
		 */
		MD("Moldova, Republic of", "MDA", 498),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Montenegro">Montenegro</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#ME">ME</a>, MNE, 499,
		 * Officially assigned]
		 */
		ME("Montenegro", "MNE", 499),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Madagascar">Madagascar</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#MG">MG</a>, MDG, 450,
		 * Officially assigned]
		 */
		MG("Madagascar", "MDG", 450),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Marshall_Islands">Marshall Islands</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#MH">MH</a>, MHL, 584,
		 * Officially assigned]
		 */
		MH("Marshall Islands", "MHL", 584),

//		/**
//		 * <a href="https://en.wikipedia.org/wiki/North_Macedonia">North Macedonia, Republic of</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#MK">MK</a>, MKD, 807,
//		 * Officially assigned]
//		 */
//		MK("North Macedonia, Republic of", "MKD", 807),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Mali">Mali</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#ML">ML</a>, MLI, 466,
		 * Officially assigned]
		 */
		ML("Mali", "MLI", 466),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/Myanmar">Myanmar</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#MM">MM</a>, MMR, 104,
//		 * Officially assigned]
//		 *
//		 * @see #BU
//		 */
//		MM("Myanmar", "MMR", 104),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Mongolia">Mongolia</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#MN">MN</a>, MNG, 496,
		 * Officially assigned]
		 */
		MN("Mongolia", "MNG", 496),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Macau">Macao</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#MO">MO</a>, MCO, 492,
		 * Officially assigned]
		 */
		MO("Macau", "MAC", 446),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Martinique">Martinique</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#MQ">MQ</a>, MTQ, 474,
		 * Officially assigned]
		 */
		MQ("Martinique", "MTQ", 474),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Mauritania">Mauritania</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#MR">MR</a>, MRT, 478,
		 * Officially assigned]
		 */
		MR("Mauritania", "MRT", 478),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Montserrat">Montserrat</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#MS">MS</a>, MSR, 500,
		 * Officially assigned]
		 */
		MS("Montserrat", "MSR", 500),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Malta">Malta</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#MT">MT</a>, MLT, 470,
		 * Officially assigned]
		 */
		MT("Malta", "MLT", 470),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Mauritius">Mauritius</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#MU">MU</a>, MUS, 480,
		 * Officially assigned]]
		 */
		MU("Mauritius", "MUS", 480),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Maldives">Maldives</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#MV">MV</a>, MDV, 462,
		 * Officially assigned]
		 */
		MV("Maldives", "MDV", 462),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Malawi">Malawi</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#MW">MW</a>, MWI, 454,
		 * Officially assigned]
		 */
		MW("Malawi", "MWI", 454),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Mexico">Mexico</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#MX">MX</a>, MEX, 484,
		 * Officially assigned]
		 */
		MX("Mexico", "MEX", 484),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Malaysia">Malaysia</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#MY">MY</a>, MYS, 458,
		 * Officially assigned]
		 */
		MY("Malaysia", "MYS", 458),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Mozambique">Mozambique</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#MZ">MZ</a>, MOZ, 508,
		 * Officially assigned]
		 */
		MZ("Mozambique", "MOZ", 508),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Namibia">Namibia</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#NA">NA</a>, NAM, 516,
		 * Officially assigned]
		 */
		NA("Namibia", "NAM", 516),

		/**
		 * <a href="http://en.wikipedia.org/wiki/New_Caledonia">New Caledonia</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#NC">NC</a>, NCL, 540,
		 * Officially assigned]
		 */
		NC("New Caledonia", "NCL", 540),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Niger">Niger</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#NE">NE</a>, NER, 562,
		 * Officially assigned]
		 */
		NE("Niger", "NER", 562),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Norfolk_Island">Norfolk Island</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#NF">NF</a>, NFK, 574,
		 * Officially assigned]
		 */
		NF("Norfolk Island", "NFK", 574),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Nigeria">Nigeria</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#NG">NG</a>, NGA, 566,
		 * Officially assigned]
		 */
		NG("Nigeria","NGA", 566),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Nicaragua">Nicaragua</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#NI">NI</a>, NIC, 558,
		 * Officially assigned]
		 */
		NI("Nicaragua", "NIC", 558),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Netherlands">Netherlands</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#NL">NL</a>, NLD, 528,
		 * Officially assigned]
		 */
		NL("Netherlands", "NLD", 528),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Norway">Norway</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#NO">NO</a>, NOR, 578,
		 * Officially assigned]
		 */
		NO("Norway", "NOR", 578),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Nepal">Nepal</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#NP">NP</a>, NPL, 524,
		 * Officially assigned]
		 */
		NP("Nepal", "NPL", 524),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Nauru">Nauru</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#NR">NR</a>, NRU, 520,
		 * Officially assigned]
		 */
		NR("Nauru", "NRU", 520),

		/**
		 * <a href="http://en.wikipedia.org/wiki/New_Zealand">New Zealand</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#NZ">NZ</a>, NZL, 554,
		 * Officially assigned]
		 */
		NZ("New Zealand", "NZL", 554),

		/**
		 * <a href=http://en.wikipedia.org/wiki/Oman"">Oman</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#OM">OM</a>, OMN, 512,
		 * Officially assigned]
		 */
		OM("Oman", "OMN", 512),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Panama">Panama</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#PA">PA</a>, PAN, 591,
		 * Officially assigned]
		 */
		PA("Panama", "PAN", 591),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Peru">Peru</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#PE">PE</a>, PER, 604,
		 * Officially assigned]
		 */
		PE("Peru", "PER", 604),

		/**
		 * <a href="http://en.wikipedia.org/wiki/French_Polynesia">French Polynesia</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#PF">PF</a>, PYF, 258,
		 * Officially assigned]
		 */
		PF("French Polynesia", "PYF", 258),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Papua_New_Guinea">Papua New Guinea</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#PG">PG</a>, PNG, 598,
		 * Officially assigned]
		 */
		PG("Papua New Guinea", "PNG", 598),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Philippines">Philippines</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#PH">PH</a>, PHL, 608,
		 * Officially assigned]
		 */
		PH("Philippines", "PHL", 608),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Pakistan">Pakistan</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#PK">PK</a>, PAK, 586,
		 * Officially assigned]
		 */
		PK("Pakistan", "PAK", 586),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Poland">Poland</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#PL">PL</a>, POL, 616,
		 * Officially assigned]
		 */
		PL("Poland", "POL", 616),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Pitcairn_Islands">Pitcairn</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#PN">PN</a>, PCN, 612,
		 * Officially assigned]
		 */
		PN("Pitcairn Island", "PCN", 612),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Puerto_Rico">Puerto Rico</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#PR">PR</a>, PRI, 630,
		 * Officially assigned]
		 */
		PR("Puerto Rico", "PRI", 630),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Portugal">Portugal</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#PT">PT</a>, PRT, 620,
		 * Officially assigned]
		 */
		PT("Portugal", "PRT", 620),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Palau">Palau</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#PW">PW</a>, PLW, 585,
		 * Officially assigned]
		 */
		PW("Palau", "PLW", 585),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Paraguay">Paraguay</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#PY">PY</a>, PRY, 600,
		 * Officially assigned]
		 */
		PY("Paraguay", "PRY", 600),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Qatar">Qatar</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#QA">QA</a>, QAT, 634,
		 * Officially assigned]
		 */
		QA("Qatar", "QAT", 634),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Romania">Romania</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#RO">RO</a>, ROU, 642,
		 * Officially assigned]
		 */
		RO("Romania", "ROU", 642),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Serbia">Serbia</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#RS">RS</a>, SRB, 688,
		 * Officially assigned]
		 */
		RS("Serbia", "SRB", 688),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/Russia">Russian Federation</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#RU">RU</a>, RUS, 643,
//		 * Officially assigned]
//		 */
//		RU("Russian Federation", "RUS", 643),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Rwanda">Rwanda</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#RW">RW</a>, RWA, 646,
		 * Officially assigned]
		 */
		RW("Rwanda", "RWA", 646),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Saudi_Arabia">Saudi Arabia</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#SA">SA</a>, SAU, 682,
		 * Officially assigned]
		 */
		SA("Saudi Arabia", "SAU", 682),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Solomon_Islands">Solomon Islands</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#SB">SB</a>, SLB, 90,
		 * Officially assigned]
		 */
		SB("Solomon Islands", "SLB", 90),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Seychelles">Seychelles</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#SC">SC</a>, SYC, 690,
		 * Officially assigned]
		 */
		SC("Seychelles", "SYC", 690),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Sudan">Sudan</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#SD">SD</a>, SDN, 729,
		 * Officially assigned]
		 */
		SD("Sudan", "SDN", 729),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Sweden">Sweden</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#SE">SE</a>, SWE, 752,
		 * Officially assigned]
		 */
		SE("Sweden", "SWE", 752),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Finland">Finland</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#SF">SF</a>, FIN, 246,
		 * Traditionally reserved]
		 *
		 * @see #FI
		 */
		SF("Finland", "FIN", 246),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Singapore">Singapore</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#SG">SG</a>, SGP, 702,
		 * Officially assigned]
		 */
		SG("Singapore", "SGP", 702),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/Saint_Helena,_Ascension_and_Tristan_da_Cunha">Saint Helena, Ascension and Tristan da Cunha</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#SH">SH</a>, SHN, 654,
//		 * Officially assigned]
//		 */
//		SH("Saint Helena, Ascension and Tristan da Cunha", "SHN", 654),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Slovenia">Slovenia</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#SI">SI</a>, SVN, 705,
		 * Officially assigned]
		 */
		SI("Slovenia", "SVN", 705),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/Svalbard_and_Jan_Mayen">Svalbard and Jan Mayen</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#SJ">SJ</a>, SJM, 744,
//		 * Officially assigned]
//		 */
//		SJ("Svalbard and Jan Mayen", "SJM", 744),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Slovakia">Slovakia</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#SK">SK</a>, SVK, 703,
		 * Officially assigned]
		 */
		SK("Slovak Republic", "SVK", 703),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Sierra_Leone">Sierra Leone</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#SL">SL</a>, SLE, 694,
		 * Officially assigned]
		 */
		SL("Sierra Leone", "SLE", 694),

		/**
		 * <a href="http://en.wikipedia.org/wiki/San_Marino">San Marino</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#SM">SM</a>, SMR, 674,
		 * Officially assigned]
		 */
		SM("San Marino", "SMR", 674),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Senegal">Senegal</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#SN">SN</a>, SEN, 686,
		 * Officially assigned]
		 */
		SN("Senegal", "SEN", 686),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Somalia">Somalia</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#SO">SO</a>, SOM, 706,
		 * Officially assigned]
		 */
		SO("Somalia", "SOM", 706),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Suriname">Suriname</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#SR">SR</a>, SUR, 740,
		 * Officially assigned]
		 */
		SR("Suriname", "SUR", 740),

		/**
		 * <a href="http://en.wikipedia.org/wiki/South_Sudan">South Sudan</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#SS">SS</a>, SSD, 728,
		 * Officially assigned]
		 */
		SS("South Sudan", "SSD", 728),

		/**
		 * <a href="http://en.wikipedia.org/wiki/S%C3%A3o_Tom%C3%A9_and_Pr%C3%ADncipe">Sao Tome and Principe</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#ST">ST</a>, STP, 678,
		 * Officially assigned]
		 */
		ST("Sao Tome and Principe", "STP", 678),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/Soviet_Union">USSR</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#SU">SU</a>, SUN, 810,
//		 * Exceptionally reserved]
//		 *
//		 * <p>
//		 * Since version 1.17, the numeric code of this entry is 810.
//		 * </p>
//		 */
//		SU("USSR", "SUN", 810),

		/**
		 * <a href="http://en.wikipedia.org/wiki/El_Salvador">El Salvador</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#SV">SV</a>, SLV, 222,
		 * Officially assigned]
		 */
		SV("El Salvador", "SLV", 222),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Sint_Maarten">Sint Maarten (Dutch part)</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#SX">SX</a>, SXM, 534,
		 * Officially assigned]
		 */
		SX("Sint Maarten", "SXM", 534),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/Syria">Syrian Arab Republic</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#SY">SY</a>, SYR, 760,
//		 * Officially assigned]
//		 */
//		SY("Syrian Arab Republic", "SYR", 760),
//
//		/**
//		 * <a href="http://en.wikipedia.org/wiki/Eswatini">Eswatini</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#SZ">SZ</a>, SWZ, 748,
//		 * Officially assigned]
//		 */
//		SZ("Eswatini", "SWZ", 748),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/Tristan_da_Cunha">Tristan da Cunha</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#TA">TA</a>, TAA, -1,
//		 * Exceptionally reserved.
//		 */
//		TA("Tristan da Cunha", "TAA", -1),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Turks_and_Caicos_Islands">Turks and Caicos Islands</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#TC">TC</a>, TCA, 796,
		 * Officially assigned]
		 */
		TC("Turks and Caicos Islands", "TCA", 796),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Chad">Chad</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#TD">TD</a>, TCD, 148,
		 * Officially assigned]
		 */
		TD("Chad", "TCD", 148),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/French_Southern_and_Antarctic_Lands">French Southern Territories</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#TF">TF</a>, ATF, 260,
//		 * Officially assigned]
//		 */
//		TF("French Southern Territories", "ATF", 260),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Togo">Togo</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#TG">TG</a>, TGO, 768,
		 * Officially assigned]
		 */
		TG("Togo", "TGO", 768),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Thailand">Thailand</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#TH">TH</a>, THA, 764,
		 * Officially assigned]
		 */
		TH("Thailand", "THA", 764),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Tajikistan">Tajikistan</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#TJ">TJ</a>, TJK, 762,
		 * Officially assigned]
		 */
		TJ("Tajikistan", "TJK", 762),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Tokelau">Tokelau</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#TK">TK</a>, TKL, 772,
		 * Officially assigned]
		 */
		TK("Tokelau", "TKL", 772),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/East_Timor">Timor-Leste</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#TL">TL</a>, TLS, 626,
//		 * Officially assigned]
//		 *
//		 * @see #TM
//		 */
//		TL("Timor-Leste", "TLS", 626),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Turkmenistan">Turkmenistan</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#TM">TM</a>, TKM, 795,
		 * Officially assigned]
		 */
		TM("Turkmenistan", "TKM", 795),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Tunisia">Tunisia</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#TN">TN</a>, TUN, 788,
		 * Officially assigned]
		 */
		TN("Tunisia", "TUN", 788),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Tonga">Tonga</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#TO">TO</a>, TON, 776,
		 * Officially assigned]
		 */
		TO("Tonga", "TON", 776),

		/**
		 * <a href="http://en.wikipedia.org/wiki/East_Timor">East Timor</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#TP">TP</a>, TMP, 626,
		 * Traditionally reserved]
		 *
		 * <p>
		 * Since version 1.16, the value of alpha-3 code of this entry is {@code TMP}
		 * (not <code><a href="http://en.wikipedia.org/wiki/ISO_3166-3#TPTL">TPTL</a></code>).
		 * </p>
		 *
		 * <p>
		 * Since version 1.17, the numeric code of this entry is 626.
		 * </p>
		 *
		 * @see
		 */
		TP("East Timor", "TMP", 626),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Turkey">Turkey</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#TR">TR</a>, TUR, 792,
		 * Officially assigned]
		 */
		TR("Turkey", "TUR", 792),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Trinidad_and_Tobago">Trinidad and Tobago</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#TT">TT</a>, TTO, 780,
		 * Officially assigned]
		 */
		TT("Trinidad and Tobago", "TTO", 780),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Tuvalu">Tuvalu</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#TV">TV</a>, TUV, 798,
		 * Officially assigned]
		 */
		TV("Tuvalu", "TUV", 798),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/Taiwan">Taiwan, Province of China</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#TW">TW</a>, TWN, 158,
//		 * Officially assigned]
//		 */
//		TW("Taiwan, Province of China", "TWN", 158),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Tanzania">Tanzania, United Republic of</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#TZ">TZ</a>, TZA, 834,
		 * Officially assigned]
		 */
		TZ("Tanzania", "TZA", 834),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Ukraine">Ukraine</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#UA">UA</a>, UKR, 804,
		 * Officially assigned]
		 */
		UA("Ukraine", "UKR", 804),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Uganda">Uganda</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#UG">UG</a>, UGA, 800,
		 * Officially assigned]
		 */
		UG("Uganda", "UGA", 800),

		/**
		 * <a href="http://en.wikipedia.org/wiki/United_Kingdom">United Kingdom</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#UK">UK</a>, null, 826,
		 * Exceptionally reserved]
		 *
		 * <p>
		 * Since version 1.17, the numeric code of this entry is 826.
		 * </p>
		 *
		 * @see #GB
		 */
		UK("United Kingdom", null, 826),

		/**
		 * <a href="http://en.wikipedia.org/wiki/United_States_Minor_Outlying_Islands">United States Minor Outlying Islands</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#UM">UM</a>, UMI, 581,
		 * Officially assigned]
		 */
		UM("US Minor Outlying Islands", "UMI", 581),

		/**
		 * <a href="http://en.wikipedia.org/wiki/United_States">United States</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#US">US</a>, USA, 840,
		 * Officially assigned]
		 */
		US("United States", "USA", 840),

		/**
		 * <a href="http://en.wikipedia.org/wiki/United_States">United States</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#US">US</a>, USA, 840,
		 * Officially assigned]
		 */
		USA("United States", "USA", 840),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Uruguay">Uruguay</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#UY">UY</a>, URY, 858,
		 * Officially assigned]
		 */
		UY("Uruguay", "URY", 858),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Uzbekistan">Uzbekistan</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#UZ">UZ</a>, UZB, 860,
		 * Officially assigned]
		 */
		UZ("Uzbekistan", "UZB", 860),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/Vatican_City">Holy See (Vatican City State)</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#VA">VA</a>, VAT, 336,
//		 * Officially assigned]
//		 */
//		VA("Holy See (Vatican City State)", "VAT", 336),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/Saint_Vincent_and_the_Grenadines">Saint Vincent and the Grenadines</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#VC">VC</a>, VCT, 670,
//		 * Officially assigned]
//		 */
//		VC("Saint Vincent and the Grenadines", "VCT", 670),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Venezuela">Venezuela, Bolivarian Republic of</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#VE">VE</a>, VEN, 862,
		 * Officially assigned]
		 */
		VE("Venezuela", "VEN", 862),

		/**
		 * <a href="http://en.wikipedia.org/wiki/British_Virgin_Islands">Virgin Islands, British</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#VG">VG</a>, VGB, 92,
		 * Officially assigned]
		 */
		VG("Virgin Islands (British)", "VGB", 92),

		/**
		 * <a href="http://en.wikipedia.org/wiki/United_States_Virgin_Islands">Virgin Islands, U.S.</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#VI">VI</a>, VIR, 850,
		 * Officially assigned]
		 */
		VI("Virgin Islands (USA)", "VIR", 850),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Vietnam">Viet Nam</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#VN">VN</a>, VNM, 704,
		 * Officially assigned]
		 */
		VN("Vietnam", "VNM", 704),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Vanuatu">Vanuatu</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#VU">VU</a>, VUT, 548,
		 * Officially assigned]
		 */
		VU("Vanuatu", "VUT", 548),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Wallis_and_Futuna">Wallis and Futuna</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#WF">WF</a>, WLF, 876,
		 * Officially assigned]
		 */
		WF("Wallis and Futuna", "WLF", 876),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Samoa">Samoa</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#WS">WS</a>, WSM, 882,
		 * Officially assigned]
		 */
		WS("Samoa", "WSM", 882),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/Northern_Ireland">Northern Ireland</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#XI">XI</a>, XXI, -1,
//		 * User assigned]
//		 *
//		 * @since 1.28
//		 */
//		XI("Northern Ireland", "XXI", -1),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Kosovo">Kosovo, Republic of</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#XK">XK</a>, XXK, -1,
		 * User assigned]
		 */
		XK("Kosovo", "XKX", -1),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Yemen">Yemen</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#YE">YE</a>, YEM, 887,
		 * Officially assigned]
		 */
		YE("Yemen", "YEM", 887),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/Mayotte">Mayotte</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#YT">YT</a>, MYT, 175,
//		 * Officially assigned]
//		 */
//		YT("Mayotte", "MYT", 175),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/Yugoslavia">Yugoslavia</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#YU">YU</a>, YUG, 890,
//		 * Traditionally reserved]
//		 *
//		 * <p>
//		 * Since version 1.16, the value of alpha-3 code of this entry is {@code YUG}
//		 * (not <code><a href="http://en.wikipedia.org/wiki/ISO_3166-3#YUCS">YUCS</a></code>).
//		 * </p>
//		 */
//		YU("Yugoslavia", "YUG", 890),

		/**
		 * <a href="http://en.wikipedia.org/wiki/South_Africa">South Africa</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#ZA">ZA</a>, ZAF, 710,
		 * Officially assigned]
		 */
		ZA("South Africa", "ZAF", 710),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Zambia">Zambia</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#ZM">ZM</a>, ZMB, 894,
		 * Officially assigned]
		 */
		ZM("Zambia", "ZMB", 894),

//		/**
//		 * <a href="http://en.wikipedia.org/wiki/Zaire">Zaire</a>
//		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#ZR">ZR</a>, ZAR, 180,
//		 * Traditionally reserved]
//		 *
//		 * <p>
//		 * Since version 1.16, the value of alpha-3 code of this entry is {@code ZAR}
//		 * (not <code><a href="http://en.wikipedia.org/wiki/ISO_3166-3#ZRCD">ZRCD</a></code>).
//		 * </p>
//		 *
//		 * <p>
//		 * Since version 1.17, the numeric code of this entry is 180.
//		 * </p>
//		 *
//		 */
//		ZR("Zaire", "ZAR", 180),

		/**
		 * <a href="http://en.wikipedia.org/wiki/Zimbabwe">Zimbabwe</a>
		 * [<a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#ZW">ZW</a>, ZWE, 716,
		 * Officially assigned]
		 */
		ZW("Zimbabwe", "ZWE", 716);


		private static final Map<String, netsuiteCountry> alpha3Map = new HashMap<String, netsuiteCountry>();
		private static final Map<String, netsuiteCountry> alpha4Map = new HashMap<String, netsuiteCountry>();
		private static final Map<Integer, netsuiteCountry> numericMap = new HashMap<Integer, netsuiteCountry>();


		private final String name;
		private final String alpha3;
		private final int numeric;

		/**
		 * Get the country name.
		 *
		 * @return
		 *         The country name.
		 */
		public String getName()
		{
			return name;
		}

		public static String getLongName(String shortName){
			String longName = netsuiteCountry.valueOf(shortName).getName();
			return longName;
		}

	netsuiteCountry(String name, String alpha3, int numeric)
	{
		this.name = name;
		this.alpha3 = alpha3;
		this.numeric = numeric;
	}

}