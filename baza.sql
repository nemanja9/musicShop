/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 10.4.13-MariaDB : Database - anmusicshop
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`anmusicshop` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `anmusicshop`;

/*Table structure for table `order_items` */

DROP TABLE IF EXISTS `order_items`;

CREATE TABLE `order_items` (
  `item_id` int(11) NOT NULL AUTO_INCREMENT,
  `itemPrice` double NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT 0,
  `product_product_id` int(11) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `FKlws6u50uwtagixdxfbfr9la9b` (`product_product_id`),
  KEY `FKbioxgbv59vetrxe0ejfubep1w` (`order_id`),
  CONSTRAINT `FKbioxgbv59vetrxe0ejfubep1w` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `FKlws6u50uwtagixdxfbfr9la9b` FOREIGN KEY (`product_product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4;

/*Data for the table `order_items` */

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_date` date DEFAULT NULL,
  `order_status` varchar(256) NOT NULL,
  `paid_date` date DEFAULT NULL,
  `paymentId` varchar(255) DEFAULT NULL,
  `shipped_date` date DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `userEntity_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `FK7cns59x6pb9ewbil8m487m7js` (`userEntity_user_id`),
  CONSTRAINT `FK7cns59x6pb9ewbil8m487m7js` FOREIGN KEY (`userEntity_user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4;

/*Data for the table `orders` */

insert  into `orders`(`order_id`,`order_date`,`order_status`,`paid_date`,`paymentId`,`shipped_date`,`token`,`userEntity_user_id`) values 
(17,'2020-08-26','POSLATO','2020-08-26','PAYID-L5DGCQQ4UU78283M4247443J',NULL,'EC-5P555843X60401923',6);

/*Table structure for table `products` */

DROP TABLE IF EXISTS `products`;

CREATE TABLE `products` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(255) NOT NULL,
  `description` varchar(3000) DEFAULT NULL,
  `imgPath` varchar(250) NOT NULL,
  `manufacturer` varchar(256) DEFAULT NULL,
  `price` double NOT NULL,
  `product_name` varchar(256) NOT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=154 DEFAULT CHARSET=utf8mb4;

/*Data for the table `products` */

insert  into `products`(`product_id`,`category`,`description`,`imgPath`,`manufacturer`,`price`,`product_name`) values 
(127,'Klavijature','<br>\r\n<br>Klavijatura<br>\r\n<br>\r\n<br>61 poluotežana dirka<br>\r\n<br>\r\n<br>Sistem<br>\r\n<br>\r\n<br>Nadogradiv operatvni sistem<br>\r\n<br>\r\n<br>Ton generator<br>\r\n<br>\r\n<br>EDS-X<br>\r\n<br>\r\n<br>Polifonija<br>\r\n<br>\r\n<br>128 nota<br>\r\n<br>\r\n<br>Efekti<br>\r\n<br>\r\n<br>Accompaniment/Song: 4 Insert Efekta; 3 Master Efekta (148 Tipova)<br>\r\n<br>Keyboard Sounds: 1 Insert Efekat; 2 Master Efekta (148 Tipova)<br>\r\n<br>Finalni Master Efekti: Waves Audio Ltd. MAXX Suite (Uključujući MaxxEQ™, MaxxBass™, MaxxTreble™, MaxxStereo™, MaxxVolume™)<br>\r\n<br>\r\n<br>Vokalni procesor<br>\r\n<br>\r\n<br>Da<br>\r\n<br>\r\n<br>Zvukovi<br>\r\n<br>\r\n<br>Više od 1800 fabričkih zvukova<br>\r\n<br>Više od 100 setova bubnjeva<br>\r\n<br>512 user programa + 128 user setova bubnjeva<br>\r\n<br>\r\n<br>Semplovi/RAM<br>\r\n<br>\r\n<br>Da, 400MB<br>\r\n<br>\r\n<br>Ritmovi<br>\r\n<br>\r\n<br>Više od 500 fabričkih ritmova<br>\r\n<br>1248 lokacija, uključujući User banke i Direct banke<br>\r\n<br>\r\n<br>MP3 plejer/recorder<br>\r\n<br>\r\n<br>Da<br>\r\n<br>\r\n<br>Sekvencer<br>\r\n<br>\r\n<br>Da x2<br>\r\n<br>\r\n<br>Ulazi<br>\r\n<br>\r\n<br>Mic: Combo XLR balansirani sa Gain kontrolom i fantomskim napajanjem<br>\r\n<br>Linijski 1: Left/Right Jack (1/4\") Linijski ulaz, balansirani<br>\r\n<br>Linijski 2: Stereo Mini-jack (1/8\") Linijski ulaz, nebalansiran<br>\r\n<br>\r\n<br>Izlazi<br>\r\n<br>\r\n<br>Audio izlaz: Left/Right, Out 1, Out 2; (1/4\" Jack) balansiran<br>\r\n<br>Izlaz za slušalice (1/4\" Jack)<br>\r\n<br>Video izlaz (RCA konektor)<br>\r\n<br>\r\n<br>Konekcije<br>\r\n<br>\r\n<br>Damper pedala, Pedala sa dodeljivom funkcijom, EC5, MIDI, SD, USB (Tip A x2, Tip B)<br>\r\n<br>\r\n<br>Displej<br>\r\n<br>\r\n<br>7\" kapacitivni TFT<br>\r\n<br>\r\n<br>Dimenzije<br>\r\n<br>\r\n<br>980 x 364.4 x 125.56 mm<br>\r\n<br>\r\n<br>Tezina<br>\r\n<br>\r\n<br>13,9kg<br>\r\n<br>\r\n<br>Ukljuceni dodaci<br>\r\n<br>\r\n<br>Kabl za napajanje, Stalak za note, Uputstvo za upotrebu, CD sa video uputstvima i softverom<br>\r\n<br>\r\n<br>Dodatni opis<br>\r\n<br>\r\n<br>\r\n','\\Korg Pa4x-61.jpg','Korg',2939.9,'Korg Pa4x-61'),
(128,'Klavijature','<br>Dostupne boje<br>Crna, bela, braon, wallnut<br>Klavijatura<br>88 RH3 (Real Weighted Hammer Action 3)<br>Podesavanje otpora dirke<br>5 nivoa<br>Polifonija<br>120 nota<br>Broj zvukova<br>30<br>Metronom<br>Da<br>Konekcije<br>Linijski izlaz, MIDI, Slušalice x2, Pedale, zvučnici, napajanje<br>Pojacalo/zvucnici<br>25W x 2<br>Dimenzije<br>1,346 mm x 347 mm x 770 mm<br>Tezina<br>35 kg<br>Ukljuceni dodaci<br>Adapter za napajanje<br>Garancija<br>60 meseci<br>Dodatni opis<br>Bluetooth konekcija','\\Korg C-1 Air WA.jpg','Korg',999.9,'Korg C-1 Air WA'),
(129,'Klavijature','<br>Sistem<br>EDS-i（Enhanced Definition Synthesis - integrated）<br>Klavijatura<br>61 dinamička dirka<br>Ton generator<br> <br>Polifonija<br>120<br>Zvukovi<br>496 multisemplova, 1014 semplova bubnjeva<br>RAM memorija<br>128MB<br>Efekti<br>5 insert, 2 master, 134 tipova<br>Sekvencer<br>Da + Audio recorder<br>Displej<br>240 x 64 LCD<br>Hard disk/Memorija<br>/<br>Ulazi<br>Linijski 3.5mm, mikrofonski 6.3mm<br>Izlazi<br>Linijski L+R, Slušalice<br>Konekcije<br>MIDI, USB tip B, SD, Switch pedale x2, Damper<br>Dimenzije<br>935 mm × 269 mm x 88 mm<br>Tezina<br>3,8 kg<br>Ukljuceni dodaci<br>Adapter za napajanje, uputstvo<br>Dodatni opis<br> ','\\Korg Kross2 - 61 GO.jpg','Korg',649.89,'Korg Kross2 - 61 GO'),
(130,'Klavijature','<br>Sistem<br>EDS-i（Enhanced Definition Synthesis - integrated）<br>Klavijatura<br>61 dinamička dirka<br>Ton generator<br> <br>Polifonija<br>120<br>Zvukovi<br>496 multisemplova, 1014 semplova bubnjeva<br>RAM memorija<br>128MB<br>Efekti<br>5 insert, 2 master, 134 tipova<br>Sekvencer<br>Da + Audio recorder<br>Displej<br>240 x 64 LCD<br>Hard disk/Memorija<br>/<br>Ulazi<br>Linijski 3.5mm, mikrofonski 6.3mm<br>Izlazi<br>Linijski L+R, Slušalice<br>Konekcije<br>MIDI, USB tip B, SD, Switch pedale x2, Damper<br>Dimenzije<br>935 mm × 269 mm x 88 mm<br>Tezina<br>3,8 kg<br>Ukljuceni dodaci<br>Adapter za napajanje, uputstvo<br>Dodatni opis<br> ','\\Korg Kross2 - 61 GR.jpg','Korg',649.89,'Korg Kross2 - 61 GR'),
(131,'Klavijature','<br>Sistem<br>EDS-i（Enhanced Definition Synthesis - integrated）<br>Klavijatura<br>61 dinamička dirka<br>Ton generator<br> <br>Polifonija<br>120<br>Zvukovi<br>496 multisemplova, 1014 semplova bubnjeva<br>RAM memorija<br>128MB<br>Efekti<br>5 insert, 2 master, 134 tipova<br>Sekvencer<br>Da + Audio recorder<br>Displej<br>240 x 64 LCD<br>Hard disk/Memorija<br>/<br>Ulazi<br>Linijski 3.5mm, mikrofonski 6.3mm<br>Izlazi<br>Linijski L+R, Slušalice<br>Konekcije<br>MIDI, USB tip B, SD, Switch pedale x2, Damper<br>Dimenzije<br>935 mm × 269 mm x 88 mm<br>Tezina<br>3,8 kg<br>Ukljuceni dodaci<br>Adapter za napajanje, uputstvo<br>Dodatni opis<br> ','\\Korg Kross2 - 61 GG.jpg','Korg',649.89,'Korg Kross2 - 61 GG'),
(132,'Klavijature','<br>\r\n<br>\r\n<br>\r\n<br>\r\n<br>Sistem<br>\r\n<br>EDS-i（Enhanced Definition Synthesis - integrated）<br>\r\n<br>\r\n<br>\r\n<br>Klavijatura<br>\r\n<br>61 dinamička dirka<br>\r\n<br>\r\n<br>\r\n<br>Ton generator<br>\r\n<br> <br>\r\n<br>\r\n<br>\r\n<br>Polifonija<br>\r\n<br>120<br>\r\n<br>\r\n<br>\r\n<br>Zvukovi<br>\r\n<br>496 multisemplova, 1014 semplova bubnjeva<br>\r\n<br>\r\n<br>\r\n<br>RAM memorija<br>\r\n<br>128MB<br>\r\n<br>\r\n<br>\r\n<br>Efekti<br>\r\n<br>5 insert, 2 master, 134 tipova<br>\r\n<br>\r\n<br>\r\n<br>Sekvencer<br>\r\n<br>Da + Audio recorder<br>\r\n<br>\r\n<br>\r\n<br>Displej<br>\r\n<br>240 x 64 LCD<br>\r\n<br>\r\n<br>\r\n<br>Hard disk/Memorija<br>\r\n<br>/<br>\r\n<br>\r\n<br>\r\n<br>Ulazi<br>\r\n<br>Linijski 3.5mm, mikrofonski 6.3mm<br>\r\n<br>\r\n<br>\r\n<br>Izlazi<br>\r\n<br>Linijski L+R, Slušalice<br>\r\n<br>\r\n<br>\r\n<br>Konekcije<br>\r\n<br>MIDI, USB tip B, SD, Switch pedale x2, Damper<br>\r\n<br>\r\n<br>\r\n<br>Dimenzije<br>\r\n<br>935 mm × 269 mm x 88 mm<br>\r\n<br>\r\n<br>\r\n<br>Tezina<br>\r\n<br>3,8 kg<br>\r\n<br>\r\n<br>\r\n<br>Ukljuceni dodaci<br>\r\n<br>Adapter za napajanje, uputstvo<br>\r\n<br>\r\n<br>\r\n<br>Dodatni opis<br>\r\n<br> <br>\r\n<br>\r\n<br>\r\n<br>\r\n<br>Sistem<br>EDS-i（Enhanced Definition Synthesis - integrated）<br>Klavijatura<br>61 dinamička dirka<br>Ton generator<br> <br>Polifonija<br>120<br>Zvukovi<br>496 multisemplova, 1014 semplova bubnjeva<br>RAM memorija<br>128MB<br>Efekti<br>5 insert, 2 master, 134 tipova<br>Sekvencer<br>Da + Audio recorder<br>Displej<br>240 x 64 LCD<br>Hard disk/Memorija<br>/<br>Ulazi<br>Linijski 3.5mm, mikrofonski 6.3mm<br>Izlazi<br>Linijski L+R, Slušalice<br>Konekcije<br>MIDI, USB tip B, SD, Switch pedale x2, Damper<br>Dimenzije<br>935 mm × 269 mm x 88 mm<br>Tezina<br>3,8 kg<br>Ukljuceni dodaci<br>Adapter za napajanje, uputstvo<br>Dodatni opis<br> ','\\Korg Kross2 - 88 .jpg','Korg',1299.9,'Korg Kross2 - 88 '),
(133,'Klavijature','<br>\r\n<br>Dostupne boje<br>\r\n<br>\r\n<br>Crna, bela<br>\r\n<br>\r\n<br>Klavijatura<br>\r\n<br>\r\n<br>88 NH dirki<br>\r\n<br>\r\n<br>Podešavanje otpora dirke<br>\r\n<br>\r\n<br>3 nivoa<br>\r\n<br>\r\n<br>Polifonija<br>\r\n<br>\r\n<br>120 nota<br>\r\n<br>\r\n<br>Broj zvukova<br>\r\n<br>\r\n<br>12<br>\r\n<br>\r\n<br>Metronom<br>\r\n<br>\r\n<br>Da<br>\r\n<br>\r\n<br>Konekcije<br>\r\n<br>\r\n<br>Slusalice/Linijski izlaz, pedale,USB<br>\r\n<br>\r\n<br>Pojačalo/zvučnici<br>\r\n<br>\r\n<br>2 x 15W<br>\r\n<br>\r\n<br>Dimenzije<br>\r\n<br>\r\n<br>1312 mm x 336 mm x 117 mm<br>\r\n<br>\r\n<br>Težina<br>\r\n<br>\r\n<br>11,4 kg<br>\r\n<br>\r\n<br>Uključeni dodaci<br>\r\n<br>\r\n<br>Sustain (damper pedala), AC adapter, Stalak za note<br>\r\n<br>\r\n<br>Garancija<br>\r\n<br>\r\n<br>60 meseci<br>\r\n<br>\r\n<br>Dodatni opis<br>\r\n<br>\r\n<br>/<br>\r\n','\\Korg B2 - BK.jpg','Korg',474.9,'Korg B2 - BK'),
(134,'Klavijature','<br>\r\n<br>Dostupne boje<br>\r\n<br>\r\n<br>Crna, bela<br>\r\n<br>\r\n<br>Klavijatura<br>\r\n<br>\r\n<br>88 NH dirki<br>\r\n<br>\r\n<br>Podešavanje otpora dirke<br>\r\n<br>\r\n<br>3 nivoa<br>\r\n<br>\r\n<br>Polifonija<br>\r\n<br>\r\n<br>120 nota<br>\r\n<br>\r\n<br>Broj zvukova<br>\r\n<br>\r\n<br>12<br>\r\n<br>\r\n<br>Metronom<br>\r\n<br>\r\n<br>Da<br>\r\n<br>\r\n<br>Konekcije<br>\r\n<br>\r\n<br>Slusalice/Linijski izlaz, pedale,USB<br>\r\n<br>\r\n<br>Pojačalo/zvučnici<br>\r\n<br>\r\n<br>2 x 15W<br>\r\n<br>\r\n<br>Dimenzije<br>\r\n<br>\r\n<br>1312 mm x 336 mm x 117 mm<br>\r\n<br>\r\n<br>Težina<br>\r\n<br>\r\n<br>11,4 kg<br>\r\n<br>\r\n<br>Uključeni dodaci<br>\r\n<br>\r\n<br>Sustain (damper pedala), AC adapter, Stalak za note<br>\r\n<br>\r\n<br>Garancija<br>\r\n<br>\r\n<br>60 meseci<br>\r\n<br>\r\n<br>Dodatni opis<br>\r\n<br>\r\n<br>/<br>\r\n','\\Korg B2 - WH.jpg','Korg',474.9,'Korg B2 - WH'),
(135,'Klavijature','<br>\r\n<br>Dostupne boje<br>\r\n<br>\r\n<br>Crna, bela<br>\r\n<br>\r\n<br>Klavijatura<br>\r\n<br>\r\n<br>88 NH dirki<br>\r\n<br>\r\n<br>Podešavanje otpora dirke<br>\r\n<br>\r\n<br>3 nivoa<br>\r\n<br>\r\n<br>Polifonija<br>\r\n<br>\r\n<br>120 nota<br>\r\n<br>\r\n<br>Broj zvukova<br>\r\n<br>\r\n<br>12<br>\r\n<br>\r\n<br>Metronom<br>\r\n<br>\r\n<br>Da<br>\r\n<br>\r\n<br>Konekcije<br>\r\n<br>\r\n<br>Slusalice/Linijski izlaz, pedale,USB<br>\r\n<br>\r\n<br>Pojačalo/zvučnici<br>\r\n<br>\r\n<br>2 x 15W<br>\r\n<br>\r\n<br>Dimenzije<br>\r\n<br>\r\n<br>1312 mm x 336 mm x 117 mm<br>\r\n<br>\r\n<br>Težina<br>\r\n<br>\r\n<br>11,4 kg<br>\r\n<br>\r\n<br>Uključeni dodaci<br>\r\n<br>\r\n<br>Sustain (damper pedala), AC adapter, Stalak za note<br>\r\n<br>\r\n<br>Garancija<br>\r\n<br>\r\n<br>60 meseci<br>\r\n<br>\r\n<br>Dodatni opis<br>\r\n<br>\r\n<br>/<br>\r\n','\\Korg B2SP - BK.jpg','Korg',629.9,'Korg B2SP - BK'),
(136,'Klavijature','<br>\r\n<br>Keyboard<br>\r\n<br>\r\n<br>SV-2/SV-2S (73 key)<br>\r\n<br>\r\n<br> <br>\r\n<br>\r\n<br>SV-2/SV-2S (88 key)<br>\r\n<br>\r\n<br>Touch selection<br>\r\n<br>\r\n<br>Eight curves<br>\r\n<br>\r\n<br>Tuning<br>\r\n<br>\r\n<br>Master Transpose, Master Tune, Tuning curves<br>\r\n<br>\r\n<br>Sounds <br>generation<br>\r\n<br>\r\n<br>EDS-X (Enhanced Definition Synthesis - eXpanded<br>\r\n<br>\r\n<br>Maximum Polyphony<br>\r\n<br>\r\n<br>128 notes<br>\r\n<br>\r\n<br>Multi Sound<br>\r\n<br>\r\n<br>Layer, Split<br>\r\n<br>\r\n<br>Factroy Sounds<br>\r\n<br>\r\n<br>72 (6 Basic sounds x 2 sets, 6 variations<br>\r\n<br>\r\n<br>EP1 ( Vintage)<br>\r\n<br>\r\n<br>MK I Suitcase, MK I Stage, MK II Suitcase, MK II Stage, MK V Stage, MK V Bright, Dyno EP, Dyno EP Bright, Wurly, Wurly Classic, Wurly Dark, Wurly Bright<br>\r\n<br>\r\n<br>EP2 ( Various)<br>\r\n<br>\r\n<br>FM Piano 1, FM Piano2, FM Piano 3, FM Piano & Pad, Pianet T, Pianet N, Hybrid Piano, Hybrid Tine, Wurly & Strings, FM & Strings, MK II & Pad, FM & Pad<br>\r\n<br>\r\n<br>Piano 1 ( Acoustic)<br>\r\n<br>\r\n<br>German Grand, German Classic, Italian Grand, Italian Bright, Japanese Grand, Japanese Bright, Austrian Grand, Austrian Classic, Japanese Upright, Upright Bright, German Upright, Rock Piano<br>\r\n<br>\r\n<br>Piano 2 ( Various)<br>\r\n<br>\r\n<br>Electric Grand, German Mono, KORG M1 Piano, KORG SG-1D, Digital piano, Electra Piano, Tack Piano, Honky-Tonk, Piano & Strings, Piano & Pad, Piano & Synth, Piano &  Brass<br>\r\n<br>\r\n<br>Clavier<br>\r\n<br>\r\n<br>Clav Ac, Clav Ad, Clav BC, Clav BD, Harpsichord, Harpsichord Oct, Perc.Organ, Rock Organ, Jazz Organ, VOX Organ, Church Organ, Pipe Organ<br>\r\n<br>\r\n<br>Other<br>\r\n<br>\r\n<br>Full Strings, Classic Strings, Tape Strings, Strings & Voices, Warm Pad, Bright Pad, Orchestra, Pizzicato and Glock, Brass, Synth Brass, Pad/Mini Lead, Pad/Synth Lead<br>\r\n<br>\r\n<br>Favorite Sounds<br>\r\n<br>\r\n<br>64 ( 8 banks x 8 variations ) for saving of customized settings<br>\r\n<br>\r\n<br>Effects<br>\r\n<br>\r\n<br> <br>\r\n<br>\r\n<br>Structure<br>\r\n<br>\r\n<br>6 effects (5 effects + 1 total effects)<br>\r\n<br>\r\n<br>Equalizer<br>\r\n<br>\r\n<br>Bass, Mid , Treble<br>\r\n<br>\r\n<br>Pre FX<br>\r\n<br>\r\n<br>Red Comp, Treble Boost, U-Vibe, Vibrato, Tremolo, VOX Wah ( Auto or Pedal control)<br>\r\n<br>\r\n<br>Amplifier models<br>\r\n<br>\r\n<br>Clean, Twin, Tweed, AC 30 , Boutique, Organ Amp + Cabinet Models, Valve Reactor Tehnology – 12 AX7 ( ECC83) valve<br>\r\n<br>\r\n<br>Modulation<br>\r\n<br>\r\n<br>Classic Chorus, Black Chorus, Orange Phaser, Small Phaser, MX Flanger, Rotary (Slow/Fast control)<br>\r\n<br>\r\n<br>Ambient<br>\r\n<br>\r\n<br>Room, Plate, Hall, Spring, Tape Echo, Stereo Delay ( Tap tempo)<br>\r\n<br>\r\n<br>Total FX ( only editable with SV-2 Editor)<br>\r\n<br>\r\n<br>Stereo Mastering Limiter, Stereo Limiter<br>\r\n<br>\r\n<br>Panel controls<br>\r\n<br>\r\n<br> <br>\r\n<br>\r\n<br>Sound Select<br>\r\n<br>\r\n<br>TYPE knob VARIATION knob, FAVORITES 1-8 switches<br>\r\n<br>\r\n<br>Effect Control<br>\r\n<br>\r\n<br>Equalizer: On/Off switch, BASS knob, MID knob, TREBLE knob<br>\r\n<br>\r\n<br>Pre FX<br>\r\n<br>\r\n<br>On/Off switch, TY','\\Korg SV-2 73.jpg','Korg',2239.89,'Korg SV-2 73'),
(137,'Klavijature','<br>\r\n<br>Dimenzije:<br>\r\n<br>\r\n<br>Širina: 1,357 mm [53-7/16\"]<br>\r\n<br>Visina: 815 mm [32-1/16\"]<br>\r\n<br>Dubina: 422 mm [16-5/8\"]<br>\r\n<br>\r\n<br>Težina:<br>\r\n<br>\r\n<br>38.0 kg (83 lbs., 12 oz)<br>\r\n<br>\r\n<br>Klavijatura:<br>\r\n<br>\r\n<br>Broj dirki: 88<br>\r\n<br>Tip: GHS klavijatura sa mat crnim završetkom<br>\r\n<br>Touch response: Hard/Medium/Soft/Fixed<br>\r\n<br>Broj pedala: 3<br>\r\n<br>Half Pedal: Da<br>\r\n<br>Funkcije: Damper/Sostenuto/Soft                          <br>\r\n<br>\r\n<br>Poklopac:<br>\r\n<br>\r\n<br>Klizeći<br>\r\n<br>\r\n<br>Stalak za note:<br>\r\n<br>\r\n<br>Da<br>\r\n<br>\r\n<br>Ton generator:<br>\r\n<br>\r\n<br>Yamaha CFX<br>\r\n<br>\r\n<br>Polifonija<br>\r\n<br>\r\n<br>192<br>\r\n<br>\r\n<br>Broj instrumenata:<br>\r\n<br>\r\n<br>10<br>\r\n<br>\r\n<br>Efekti:<br>\r\n<br>\r\n<br>Reverb: 4 tipa<br>\r\n<br>Intelligent Acoustic Control (IAC): Da<br>\r\n<br>Stereophonic Optimizer: Da<br>\r\n<br>Damper Resonance: Da<br>\r\n<br>\r\n<br>Snimanje:<br>\r\n<br>\r\n<br>Broj pesama: 1<br>\r\n<br>Broj Track-ova: 2<br>\r\n<br>Kapacitet memorije: 100 KB/Song (Approx. 11,000 notes)<br>\r\n<br>\r\n<br>Kompatibilni formati:<br>\r\n<br>\r\n<br>Snimanje i reprodukcija: Standard MIDI File (SMF) Format 0 & 1<br>\r\n<br>\r\n<br>Metronom:<br>\r\n<br>\r\n<br>Da<br>\r\n<br>Raspon tempa: 5 – 280<br>\r\n<br>\r\n<br>Transponovanje:<br>\r\n<br>\r\n<br>-6 – 0 – +6<br>\r\n<br>\r\n<br>Fino štimovanje:<br>\r\n<br>\r\n<br>414.8 – 440.0 – 466.8 Hz<br>\r\n<br>\r\n<br>Memorija:<br>\r\n<br>\r\n<br>900 KB<br>\r\n<br>\r\n<br>Izlaz za slušalice:<br>\r\n<br>\r\n<br>2 x Standardni Jack<br>\r\n<br>\r\n<br>Zvučnici:<br>\r\n<br>\r\n<br>2 x 8W, 12 cm<br>\r\n<br>\r\n<br>Potrošnja struje:<br>\r\n<br>\r\n<br>9W (sa PA-150 AC adapterom)<br>\r\n<br>\r\n<br>Strujni adapter:<br>\r\n<br>\r\n<br>Yamaha PA-150<br>\r\n','\\Yamaha Arius YDP-144.jpg','Yamaha',1013.47,'Yamaha Arius YDP-144'),
(138,'Gitare','<br>Tip<br>Double-cutaway solid električna gitara<br>Prednja ploča<br>Solid mahagonij<br>Zadnja ploča i stranice<br>Afrički mahagonij<br>Vrat<br>Glued-in mahagonij<br>Fingerboard<br>Ruža<br>Pragovi<br>22 medium-jumbo<br>Hardware<br>Super Smooth čivije i MaxConnect bridge<br>Magneti<br>CoAxe<br>Kontrole<br>Volume i Tone<br>Prekidači<br>\r\n<br>- 3 pozicije biranja neck, neck/bridge ili bridge magneta,<br>\r\n<br>- <br>2 pozicije biranja single ili double namotaja kod svakog magneta<br>\r\n<br>Boja<br>Transpared Red<br>Garancija<br>24 meseca<br>Dodatni opis<br>Soft case ide uz gitaru','\\Vox SDC33 TR.jpg','Vox',499.89,'Vox SDC33 TR'),
(139,'Gitare','<br>SPECIFICATIONS<br>\r\n<br>\r\n<br>\r\n<br>\r\n<br>\r\n<br>\r\n<br>\r\n<br>BODY<br>\r\n<br>Poplar<br>\r\n<br>\r\n<br>NECK<br>\r\n<br>Maple<br>479mm (18 3/4″) Scale Length<br>19 x Medium Jumbo Frets<br>Purple Heart Fingerboard<br>42mm (1.654″) Nut<br>\r\n<br>\r\n<br>PICKUPS<br>\r\n<br>1 x Mini Humbucker<br>\r\n<br>\r\n<br>CONTROLS<br>\r\n<br>1 x Volume, 1 x Tone<br>\r\n<br>\r\n<br>OUTPUTS<br>\r\n<br>1 x Normal Guitar Jack<br>\r\n<br>\r\n<br>ACCESSORIES (INCLUDED)<br>\r\n<br>1 x Neck Adjustment wrench<br>1 x Gig bag<br>\r\n<br>\r\n<br>AVAILABLE COLORS<br>\r\n<br>Black, Red, White<br>\r\n<br>\r\n<br>\r\n<br>\r\n<br>\r\n<br>\r\n<br>Specifications and features are subject to change without notice.<br>\r\n<br>\r\n<br>\r\n','\\Vox SDC-1 MINI  mini guitar.jpg','Vox',224.19,'Vox SDC-1 MINI  mini guitar'),
(140,'Gitare','<br>\r\n<br>Model Name: Bullet® Strat® with Tremolo, Rosewood Fingerboard<br>\r\n<br>Series: Bullet<br>\r\n<br>Country Of Origin: ID<br>\r\n<br>Body: Basswood<br>\r\n<br>Body Finish: Polyurethane<br>\r\n<br>Body Shape: Stratocaster®<br>\r\n<br>Neck Material: Maple<br>\r\n<br>Neck Finish: Satin Urethane<br>\r\n<br>Neck Shape: \"C\" Shape<br>\r\n<br>Scale Length: 25.5\" (648 mm)<br>\r\n<br>Fingerboard: Rosewood<br>\r\n<br>Fingerboard Radius: 9.5\" (241 mm)<br>\r\n<br>Number of Frets: 21<br>\r\n<br>Frets Size: Medium Jumbo<br>\r\n<br>String Nut: Synthetic Bone<br>\r\n<br>Nut Width: 1.650\" (42 mm)<br>\r\n<br>Position Inlays: Pearloid Dots<br>\r\n<br>Truss Rods: Standard<br>\r\n<br>Bridge Pickup: Standard Single-Coil Strat®<br>\r\n<br>Middle Pickup: Standard Single-Coil Strat®<br>\r\n<br>Neck Pickup: Standard Single-Coil Strat®<br>\r\n<br>Controls: Master Volume, Tone 1. (Neck Pickup), Tone 2. (Middle Pickup)<br>\r\n<br>Pickup Switching: 5-Position Blade: Position 1. Bridge Pickup, Position 2. Bridge and Middle Pickup, Position 3. Middle Pickup, Position 4. Middle and Neck Pickup, Position 5. Neck Pickup<br>\r\n<br>Pickup Configuration: SSS<br>\r\n<br>Bridge: 6-Saddle Vintage-Style Synchronized Tremolo<br>\r\n<br>Hardware Finish: Chrome<br>\r\n<br>Tuning Machines: Standard Die-Cast<br>\r\n<br>Pickguard: 1-Ply White<br>\r\n<br>Control Knobs: White Plastic<br>\r\n<br>Strings: Fender® USA 250L, NPS (.009-.042 Gauges)<br>\r\n<br>Unique Features: Slim (42mm) Body Profile, Traditional Strat®; Headstock Shape, White Dot Position Inlays<br>\r\n<br>Included Accessories: None<br>\r\n<br>Dimensions: 3.90x14.90x44.10 IN<br>\r\n<br>Weight: 11.00 LB<br>\r\n','\\Squier By Fender Bullet Stratocater with Tremolo RW BLK .jpg','Squier',154.99,'Squier By Fender Bullet Stratocater with Tremolo RW BLK '),
(141,'Gitare','<br>Tip<br>Single-cutaway solid električna gitara<br>Prednja ploča<br>Solid jasen<br>Zadnja ploča i stranice<br>Afrički mahagonij<br>Vrat<br>Glued-in mahagonij<br>Fingerboard<br>Ruža<br>Pragovi<br>22 medium-jumbo<br>Hardware<br>Super Smooth čivije i MaxConnect bridge<br>Magneti<br>CoAxe<br>Kontrole<br>Volume i Tone<br>Prekidači<br>\r\n<br>- 3 pozicije biranja neck, neck/bridge ili bridge magneta,<br>\r\n<br>- 3 pozicije biranja kod svakog magneta: single, P-90 ili humbucker<br>\r\n<br>Boja<br>Vintage Cream<br>Garancija<br>24 meseca<br>Dodatni opis<br>Soft case ide uz gitaru','\\Vox SSC55 VC.jpg','Vox',774.9,'Vox SSC55 VC'),
(142,'Gitare','<br>Tip<br>Single-cutaway solid električna gitara<br>Prednja ploča<br>Solid jasen<br>Zadnja ploča i stranice<br>Afrički mahagonij<br>Vrat<br>Glued-in mahagonij<br>Fingerboard<br>Ruža<br>Pragovi<br>22 medium-jumbo<br>Hardware<br>Super Smooth čivije i MaxConnect bridge<br>Magneti<br>CoAxe<br>Kontrole<br>Volume i Tone<br>Prekidači<br>\r\n<br>- 3 pozicije biranja neck, neck/bridge ili bridge magneta,<br>\r\n<br>- 3 pozicije biranja kod svakog magneta: single, P-90 ili humbucker<br>\r\n<br>Boja<br>Black<br>Garancija<br>24 meseca<br>Dodatni opis<br>Soft case ide uz gitaru','\\Vox SSC55 BK.jpg','Vox',774.9,'Vox SSC55 BK'),
(143,'Gitare','<br>Tip<br>Double-cutaway solid električna gitara<br>Prednja ploča<br>Solid mahagonij<br>Zadnja ploča i stranice<br>Afrički mahagonij<br>Vrat<br>Glued-in mahagonij<br>Fingerboard<br>Ruža<br>Pragovi<br>22 medium-jumbo<br>Hardware<br>Super Smooth čivije i MaxConnect bridge<br>Magneti<br>CoAxe<br>Kontrole<br>Volume i Tone<br>Prekidači<br>\r\n<br>- 3 pozicije biranja neck, neck/bridge ili bridge magneta,<br>\r\n<br>- <br>2 pozicije biranja single ili double namotaja kod svakog magneta<br>\r\n<br>Boja<br>Transpared Red<br>Garancija<br>24 meseca<br>Dodatni opis<br>Soft case ide uz gitaru','\\Vox SDC33 TR.jpg','Vox',499.89,'Vox SDC33 TR'),
(144,'Gitare','<br>\r\n<br>Model Name: Bullet® Strat® with Tremolo, Rosewood Fingerboard<br>\r\n<br>Series: Bullet<br>\r\n<br>Country Of Origin: ID<br>\r\n<br>Body: Basswood<br>\r\n<br>Body Finish: Polyurethane<br>\r\n<br>Body Shape: Stratocaster®<br>\r\n<br>Neck Material: Maple<br>\r\n<br>Neck Finish: Satin Urethane<br>\r\n<br>Neck Shape: \"C\" Shape<br>\r\n<br>Scale Length: 25.5\" (648 mm)<br>\r\n<br>Fingerboard: Rosewood<br>\r\n<br>Fingerboard Radius: 9.5\" (241 mm)<br>\r\n<br>Number of Frets: 21<br>\r\n<br>Frets Size: Medium Jumbo<br>\r\n<br>String Nut: Synthetic Bone<br>\r\n<br>Nut Width: 1.650\" (42 mm)<br>\r\n<br>Position Inlays: Pearloid Dots<br>\r\n<br>Truss Rods: Standard<br>\r\n<br>Bridge Pickup: Standard Single-Coil Strat®<br>\r\n<br>Middle Pickup: Standard Single-Coil Strat®<br>\r\n<br>Neck Pickup: Standard Single-Coil Strat®<br>\r\n<br>Controls: Master Volume, Tone 1. (Neck Pickup), Tone 2. (Middle Pickup)<br>\r\n<br>Pickup Switching: 5-Position Blade: Position 1. Bridge Pickup, Position 2. Bridge and Middle Pickup, Position 3. Middle Pickup, Position 4. Middle and Neck Pickup, Position 5. Neck Pickup<br>\r\n<br>Pickup Configuration: SSS<br>\r\n<br>Bridge: 6-Saddle Vintage-Style Synchronized Tremolo<br>\r\n<br>Hardware Finish: Chrome<br>\r\n<br>Tuning Machines: Standard Die-Cast<br>\r\n<br>Pickguard: 1-Ply White<br>\r\n<br>Control Knobs: White Plastic<br>\r\n<br>Strings: Fender® USA 250L, NPS (.009-.042 Gauges)<br>\r\n<br>Unique Features: Slim (42mm) Body Profile, Traditional Strat®; Headstock Shape, White Dot Position Inlays<br>\r\n<br>Included Accessories: None<br>\r\n<br>Dimensions: 3.90x14.90x44.10 IN<br>\r\n<br>Weight: 11.00 LB<br>\r\n','\\Squier By Fender Bullet Stratocaster with Tremolo RW BSB električna gitara.jpg','Squier',154.99,'Squier By Fender Bullet Stratocaster with Tremolo RW BSB električna gitara'),
(145,'Gitare','<br>SPECIFICATIONS<br>\r\n<br>KEY FEATURES<br>\r\n<br>Body ash<br>Neck maple<br>Fretboard maple<br>Frets 21 (perloid)<br>Machine heads self-locking chrome<br>Bridge Wilkinson vintage tremolo<br>Pick ups 3 Wilkinson Vintage single coils<br>Switch 5 way<br>Pots volume/tone/tone<br>Pickguard white<br>Colour tobacco sunburst','\\Soundsation ROCKER PRO 200 MP TBS.jpg','Soundsation',358.8,'Soundsation ROCKER PRO 200 MP TBS'),
(146,'Gitare','<br>KEY FEATURES<br>\r\n<br>Body solid alder<br>Neck maple<br>Tastiera rosewood<br>Frets 21<br>Machine heads chrome<br>Bridge vintage tremolo<br>Pick ups 2 humbuckers<br>Switch 3 way<br>Pots volume/tone<br>Pickguard perloid<br>Colour sonic blue','\\Soundsation SJAG611PH-SNB.jpg','Soundsation',171.09,'Soundsation SJAG611PH-SNB'),
(147,'Gitare','<br>Tip<br>Single-cutaway solid električna gitara<br>Prednja ploča<br>Solid jasen<br>Zadnja ploča i stranice<br>Afrički mahagonij<br>Vrat<br>Glued-in mahagonij<br>Fingerboard<br>Ruža<br>Pragovi<br>22 medium-jumbo<br>Hardware<br>Super Smooth čivije i MaxConnect bridge<br>Magneti<br>CoAxe<br>Kontrole<br>Volume i Tone<br>Prekidači<br>\r\n<br>- 3 pozicije biranja neck, neck/bridge ili bridge magneta,<br>\r\n<br>- 3 pozicije biranja kod svakog magneta: single, P-90 ili humbucker<br>\r\n<br>Boja<br>Gold Top<br>Garancija<br>24 meseca<br>Dodatni opis<br>Soft case ide uz gitaru','\\Vox SSC55 GL.jpg','Vox',774.9,'Vox SSC55 GL'),
(148,'Gitare','<br>Tip<br>Double-cutaway solid električna gitara<br>Prednja ploča<br>Solid jasen<br>Zadnja ploča i stranice<br>Afrički mahagonij<br>Vrat<br>Glued-in mahagonij<br>Fingerboard<br>Ruža<br>Pragovi<br>22 medium-jumbo<br>Hardware<br>Super Smooth čivije i MaxConnect bridge<br>Magneti<br>CoAxe<br>Kontrole<br>Volume i Tone<br>Prekidači<br>\r\n<br>- 3 pozicije biranja neck, neck/bridge ili bridge magneta<br>\r\n<br>- 3 pozicije biranja kod svakog magneta: single, P-90 ili humbucker<br>\r\n<br>Boja<br>Tea Burst<br>Garancija<br>24 meseca<br>Dodatni opis<br>Soft case ide uz gitaru','\\Vox SDC55 GL.jpg','Vox',779.44,'Vox SDC55 GL'),
(149,'Ozvucenje','<br>Dual 15\" subwoofer, bi-amp, active speaker<br>40 Hz to 160 Hz<br>135 dB (Half Space)<br>Class D<br>2400 W peak / 1200 W cont. (600 W x 2)<br>2x XLR/TRS Combo (balanced)<br>10 kΩ<br>2x Through output XLR (balanced)<br>2x 15-inch LF cone driver<br>15 mm birch plywood, bandpass type<br>Impact-resistant, black semi-matt textured<br>4 (on both sides)<br>M20 screw socket<br>Thermal limiting, over-temperature muting, output overcurrent<br>DC offset removal, stationary high frequency protection, input voltage limiter (Peak/RMS)<br>Primary AC mains overvoltage protection, Primary AC mains overcurrent protection<br>100 V (50 Hz/60 Hz) / 110 V to 240 V (50 Hz/60 Hz)<br>240 W<br>743 x 524 x 811 mm<br>(29.3 x 20.6 x 31.9 inch)<br>55.9 kg (123.2 lbs)','\\Pioneer XPRS 215S.jpg','Pioneer',2038.65,'Pioneer XPRS 215S'),
(150,'Ozvucenje','<br>Output Power: 2000 W peak (1300 LF + 700 HF) 1000 W continuous RMS (650 LF + 350 HF)\r\nLF Driver: 12-inch (305 mm) LF driver, 3-inch (76 mm) high-temperature voice coil\r\nHF Driver: 1.4-inch (35 mm) neodymium magnet HF driver with precision wave guide\r\nCrossover: 2 kHz\r\nMaximum SPL: 131 dB peak, 128 dB continuous (dB SPL @ 1 m)\r\nFrequency Response: 53 Hz – 20 kHz (+/- 3 dB)\r\nFrequency Range: 46 Hz – 22 kHz (-10 dB)\r\nHorn Coverage: 90° H x 60° V nominal\r\nEqualization: Contour Switch Low and High Frequency +3dB Boost\r\nConnectors: (2) XLR/TRS 1/4” (6.35 mm) combo inputs, (1) XLR link output, (1) IEC power cable input\r\nControls: (2) Volume, Power on/off switch, Contour switch, Ground-Lift switch\r\nIndicators: 2 x Power LED (front and rear), Clip limiter LED\r\nProtection: Electronic clip, Thermal and transducer overdrive protection\r\nPower Connection: IEC with Mains Fuse\r\nAC Voltage Input: 100,110-120,220–240 V, 50/60 Hz\r\nEnclosure: Trapezoidal, injection-molded, polypropylene enclosure, with perforated steel grille\r\nMounting/Installation: 36 mm standard pole mount, Wall or ceiling mount using TSB125 bracket, Flown application with integral M10 suspension points, Wedge monitor\r\nDimensions: (H x W x D): 23.8” x 13.9” x 13.8” / 605mm x 354mm x 350mm\r\nNet Weight: 35.9 lbs. / 16.3 kg.\r\nDesign Origin: Designed and tuned in the USA\r\nAccessories: TSB125 Bracket, TS212 Cover\r\n','\\Alto TS312.jpg','Alto',394.8,'Alto TS312'),
(151,'Ozvucenje','<br>Output Power: 2000 W peak (1300 LF + 700 HF) 1000 W continuous RMS (650 LF + 350 HF)\r\nLF Driver: 10-inch (254 mm) LF driver, 2.5-inch (65 mm) high-temperature voice coil\r\nHF Driver: 1.4-inch (35 mm) neodymium magnet HF driver with precision waveguide\r\nCrossover: 2.5 kHz\r\nMaximum SPL: 129 dB peak, 126 dB continuous (dB SPL @ 1 m)\r\nFrequency Response: 54 - 20 kHz (+/- 3 dB)\r\nFrequency Range: 48 Hz - 22 kHz (-10 dB)\r\nHorn Coverage: 90° H x 60° V nominal\r\nEqualization: Contour Switch Low and High Frequency +3dB Boost\r\nConnectors: (2) XLR/TRS 1/4” (6.35 mm) combo inputs, (1) XLR link output, (1) IEC power cable input\r\nControls: (2) Volume, Power on/off switch, Contour switch, Ground-Lift switch\r\nIndicators: 2 x Power LED (front and rear), Clip limiter LED\r\nProtection: Electronic clip, Thermal and transducer overdrive protection\r\nPower Connection: IEC with Mains Fuse\r\nAC Voltage Input: 100,110-120,220–240 V, 50/60 Hz\r\nEnclosure: Trapezoidal, injection-molded, polypropylene enclosure, with perforated steel grille\r\nMounting/Installation: 36 mm standard pole mount, Wall or ceiling mount using TSB810 bracket, Flown application with integral M10 suspension points, Wedge monitor\r\nDimensions: (H x W x D): 20.6” x 12.6” x 11.8” / 524mm x 320mm x 300mm\r\nWeight Net Weight: 26.1 lbs. / 11.9 kg.\r\nDesign Origin: Designed and tuned in the USA\r\nAccessories: TSB810 Bracket, TS210 Cover\r\n','\\Alto TS310.jpg','Alto',341,'Alto TS310'),
(152,'Ozvucenje','<br>Output Power: 2000 W peak, 1000 W continuous\r\nAmplifier: High-efficiency Class D amplifier\r\n15” (381 mm) woofer, 3” (76 mm) voice coil\r\nFrequency Response: 44Hz – 85Hz (- 3 dB)\r\nFrequency Range: 37Hz – 100Hz (-10 dB)\r\nMaximum SPL: 131 dB (@ 1 m) 128 dB continuous (dB SPL @ 1 m)\r\nConnections: (2) XLR+1/4” (6.35 mm) inputs; (2) XLR outputs; (1) IEC power cable input\r\nInput Level: Line +4 dBu (nominal)\r\nExternal Controls: Volume control, DSP output mode switch, polarity-reverse switch, extended low-frequency switch, power switch\r\nIndicators: Signal/clip limiter LED, six DSP output mode LEDs, polarity-reverse LED, extended low-frequency LED, power switch LED\r\nElectronic Protections: Electronic clip, thermal and transducer overdrive protection\r\nEnclosure: Internally braced MDF cabinet; 36 mm mounting pole socket on top panel\r\nRugged, mar-resistant splatter-spray finish always looks good, even with heavy use\r\nPower Connection: IEC with Mains Fuse\r\nAC Voltage Input: 100,110-120,220–240 V, 50/60 Hz\r\nConsumption: 1000 W Fuse: T10AL AC250V (100–120 V) or T5AL AC250V (220–240 V)\r\nDimensions (H x W x D): 21.3” x 20.6” x 24.7” / 542mm x 523mm x 628mm\r\nWeight: 78.48 lbs. / 35.6 kg\r\nDesign Origin: Designed and tuned in the USA\r\nAccessories: TS315S Cover','\\Alto TS315S.jpg','Alto',589.89,'Alto TS315S'),
(153,'Ozvucenje','<br>Technical Data<br>\r\n<br>Speaker Type: 2-Way Active Speaker<br>\r\n<br>Acoustical data<br>\r\n<br>Usable Bandwidth (-10dB):   51-20.000 Hz<br>\r\n<br>Frequency Response (-6 dB) :  57 - 19.700 Hz<br>\r\n<br>Max SPL:  126.5 dB<br>\r\n<br>Type HF:  Compression Driver<br>\r\n<br>HF voice coil:  1\'\'<br>\r\n<br>LF :  15\'\'<br>\r\n<br>LF voice coil: 2\'\'<br>\r\n<br>Crossover Frequency : 2100 Hz (24 dB/oct.)<br>\r\n<br>Horizontal Dispersion: asymmetrical 85° up / 120° down<br>\r\n<br>Vertical Dispersion:  85°  (+25/ -60°)<br>\r\n<br>Amplifier<br>\r\n<br>Amp Technology: Custom Made<br>\r\n<br>Amp Class:  Class D- Passice cooling (convection)<br>\r\n<br>Power Peak : 400 W<br>\r\n<br>Processor<br>\r\n<br>Controller:  DSP 28/56 bit- 48 Hz<br>\r\n<br>Limiter: Peak, RMS, Thermal<br>\r\n<br>Controls:  Volume, Audio Input Sensitivity, DSP Preset<br>\r\n<br>Input<br>\r\n<br>Main Connection: VDE<br>\r\n<br>Signal Input: Balanced 1x Combo IN (MIC/LINE)<br>\r\n<br>SIGNAL Out:  (Balanced) 1xXLR OUT (LINK)<br>\r\n<br>Mechanics<br>\r\n<br>Housing: Polypropylene<br>\r\n<br>Grille:  Full Metal Grille<br>\r\n<br>Handles: 3 (2 on side 1 on top)<br>\r\n<br>Pole Mount:  36 mm<br>\r\n<br>Width:  415 mm (16.34 in)<br>\r\n<br>Height:  725 mm (28.54 in)<br>\r\n<br>Depth: 391 mm (15.39 in)<br>\r\n<br>Weight:  17.4 kg (38.36 ibs)<br>\r\n<br>Angled Up:  Double wedge andgle  45°','\\dB Technologies B-HYPE 15.jpg','dB',432,'dB Technologies B-HYPE 15');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `adress` varchar(256) DEFAULT NULL,
  `city` varchar(256) DEFAULT NULL,
  `email` varchar(256) NOT NULL,
  `emailConfirmed` int(11) NOT NULL,
  `emailToken` varchar(256) DEFAULT NULL,
  `firstname` varchar(55) DEFAULT NULL,
  `lastname` varchar(55) DEFAULT NULL,
  `password` varchar(256) NOT NULL,
  `phone_number` varchar(25) DEFAULT NULL,
  `user_role` varchar(256) NOT NULL,
  `zip` varchar(10) DEFAULT NULL,
  `passwordToken` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

/*Data for the table `users` */

insert  into `users`(`user_id`,`adress`,`city`,`email`,`emailConfirmed`,`emailToken`,`firstname`,`lastname`,`password`,`phone_number`,`user_role`,`zip`,`passwordToken`) values 
(1,'Dunavska 34','Mladenovac','nj@nj.com',1,'fyqhhearjdpgrqgdzjus','Nemanja','sdf','a','12313','ROLE_ADMIN','123',NULL),
(6,'Dunavska 34','Mladenovac','nemanjajurisic@gmail.com',1,NULL,'Nemanja','Jurisic','a','54678786','ROLE_USER','11400',NULL),
(7,'Dunavska 34','Mladenovac','nj@nj.coms',0,'qtkjjxtjldhoqryvqdqw','Nemanja','sdf','txueryerizrogxmsktpw','12313','ROLE_USER','123','cjksjwjdmmuouzcbiczu'),
(8,'korisnik','jebeni','s@s.ss',0,'ujejzxreqbijupbcejsq','dodat','novi','hseexjdxgwwfmvlboosr','6789','ROLE_USER','123','pghnvztxjbpvtbskwohz');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
