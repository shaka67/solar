/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.i18n;

import com.ouer.solar.StringPool;
import com.ouer.solar.logger.Logger;
import com.ouer.solar.logger.LoggerFactory;

/**
 * 提供简繁体转换的功能
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月15日 下午7:03:06
 */
public abstract class GBKMap {

    private static final Logger logger = LoggerFactory.getLogger(GBKMap.class);

    private static final String GBK_T2S_T_MAP =
            "丟並亂亙亞伕佇佈佔併來侖侶侷俁係俠倀倆倉個們倖倣倫偉側偵偸偺偽傑傖傘備傚傢傭傯傳傴債傷傾僂僅僉僑僕僞僥僨僱價儀儂億儅儈儉儐儔儕儘償優儲儷儸儹儺儻儼兇兌兒兗內兩冊冪凃凍凜凟処凱凴別刪剄則剋剗剛剝剮剴創剷劃劄劇劉劊劌劍劑劒勁動勗務勛勝勞勢勣勦勩勱勳勵勸勻匭匯匱匲匳匵區協卹卻厙厛厠厤厭厰厲厴參叢吳呂咼員哢唄唕唚唸問啓啗啞啟啢啣喒喚喦喪喫喬單喲嗆嗇嗊嗎嗚嗩嗶嘆嘍嘔嘖嘗嘜嘩嘮嘯嘰嘵嘸嘽噁噅噓噝噠噥噦噯噲噴噸噹嚀嚇嚌嚐嚕嚙嚥嚦嚨嚮嚲嚳嚴嚶嚻囀囁囂囅囈囉囌囑囘囪囬囯圇國圍園圓圖團坰垵垻埡埰執堅堊堖堝堦堯報場堿塊塋塏塒塗塚塢塤塲塵塹塼墊墜墝墮墰墳墶墻墾壇壋壎壓壘壙壚壜壞壟壠壢壩壪壯壺壼壽夀夠夢夾奐奧奩奪奮妝姍姦姪娛娬婁婦婬婭媧媮媯媼媽嫋嫗嫵嫺嫻嫿嬀嬈嬋嬌嬙嬝嬡嬤嬪嬭嬰嬸嬾孃孌孫學孿宮寢實寧審寫寬寵寶尅將專尋對導尲尷屆屍屓屜屢層屨屬屭岡峴島峽崍崐崑崗崙崠崢崬崳嵐嵗嶁嶄嶇嶔嶗嶠嶢嶧嶨嶮嶴嶸嶺嶼嶽巋巒巔巖巰巹帥師帳帶幀幃幗幘幟幣幫幬幱幹幾庫廁廂廄廈廎廕廚廝廟廠廡廢廣廩廬廳廻廼弔弳張強彆彈彊彌彎彙彜彞彠彥彫彿徑從徠復徬徴徹恆恥悅悵悶悽惡惱惲惻愛愜愨愴愷愾慄慇態慍慘慙慚慟慣慤慪慫慮慳慴慶慼慾憂憊憐憑憒憖憚憤憫憮憲憶懇應懌懍懕懞懟懣懨懲懶懷懸懺懼懽懾戀戇戔戧戩戯戰戲戶扡拋挾捨捫捲掃掄掗掙掛採揀揚換揮揹搆搇損搖搗搶搾摑摜摟摣摯摳摶摻撈撏撐撓撚撟撣撥撫撲撳撻撾撿擁擄擇擊擋擔擕據擠擡擣擧擬擯擰擱擲擴擷擺擻擼擾攄攆攏攔攖攙攛攜攝攢攣攤攩攪攬攷敍敗敘敭敵數敺斂斃斕斬斷旂昇時晉晝暈暉暘暢暫暱曄曆曇曉曏曖曠曡曨曬書會朧東枴柵柺桿梔梘條梟梱棄棖棗棟棧棬棲棶椏楊楓楨業極榦榪榮榿槃構槍槓槧槨槳槼樁樂樅樑樓標樞樣樷樸樹樺橈橋機橢橫橰檁檉檔檜檟檢檣檮檯檳檸檻檾櫂櫃櫓櫚櫛櫝櫞櫟櫥櫧櫨櫪櫫櫬櫳櫸櫺櫻欄權欏欑欒欖欞欽歎歐歗歛歟歡歲歷歸歿殘殞殤殫殮殯殲殺殼毀毆毉毧毬毿氂氈氊氌氣氫氬氳氹氾汎汙汚決沍沒沖況洩洶浹涇涖涼淒淚淥淨淩淪淵淶淺渙減渢渦測渾湊湞湣湧湯溈準溝溫溮溳溼滄滅滌滎滙滬滯滲滷滸滻滾滿漁漊漚漢漣漬漲漵漸漿潁潑潔潙潛潟潤潯潰潷潿澀澁澂澆澇澗澠澤澦澩澮澱濁濃濔濕濘濛濜濟濤濫濬濰濱濶濺濼濾瀅瀆瀉瀋瀏瀕瀘瀝瀟瀠瀦瀧瀨瀰瀲瀾灃灄灑灕灘灝灣灤灧灩災炤為烏烴無煆煇煉煒煖煙煢煥煩煬熒熗熱熲熾燁燄燈燉燐燒燙燜營燦燬燭燴燻燼燾燿爍爐爗爛爭爲爺爾牀牆牋牐牘牴牽犖犛犢犧狀狹狽猙猶猻獁獃獄獅獎獨獪獫獮獰獲獵獷獸獺獻獼玀玆玨珮現琍琯琺琿瑉瑋瑒瑣瑤瑩瑪瑯瑲璉璡璣璦璫環璵璽璿瓊瓏瓔瓚甌甎甕甖產産甦畝畢畫異畱當疇疊痙痠痺瘂瘉瘋瘍瘓瘞瘡瘧瘮瘺瘻療癆癇癉癒癘癟癡癢癤癥癧癩癬癭癮癰癱癲發皚皰皸皺盃盜盞盡監盤盧盪眎眡眥眾睏睜睞瞘瞞瞼矇矓矚矯砲硃硜硤硨硯硶碩碭碸確碼磑磚磣磧磯磽礄礎礙礦礪礫礬礮礱祐祕祿禍禎禕禦禪禮禰禱禿秈稅稈稜稟稭種稱穀穌積穎穠穡穢穨穩穫穭窩窪窮窯窰窶窺竄竅竇竈竊竝竪競筆筍筧箇箋箏箠節範築篋篛篠篤篩篳簀簍簑簞簡簣簫簷簹簽簾籃籌籐籙籜籟籠籢籤籥籩籪籬籮籲粧粬粵糝糞糢糧糰糲糴糶糾紀紂約紅紆紇紈紉紋納紐紓純紕紖紗紘紙級紛紜紝紡紥紮細紱紲紳紵紹紺紼紿絀終絃組絆絍絎絏結絕絛絝絞絡絢給絨絰統絲絳絹綁綃綆綈綉綌綏綑經綜綞綠綢綣綫綬維綯綰綱網綳綴綵綸綹綺綻綽綾綿緄緇緊緋緍緒緓緔緗緘緙線緜緝緞締緡緣緤緦編緩緬緯緱緲練緶緹緻緼縂縈縉縊縋縐縑縕縗縚縛縝縞縟縣縧縫縭縮縯縱縲縴縵縶縷縹總績繃繅繆繈繒織繕繖繙繚繞繡繢繦繩繪繫繭繮繯繰繳繹繼繽繾纇纈纊續纍纏纓纖纘纜缽罈罋罌罎罏罰罵罷罸羅羆羈羋羢羥羨義羶習翬翹翺翽耑耡耬耮聖聞聯聰聲聳聵聶職聹聼聽聾肅脅脇脈脛脣脩脫脹腎腖腡腦腫腳腸膁膃膕膚膠膩膽膾膿臉臍臏臒臘臚臟臠臢臥臨臯臺與興舉舊舖舘艙艢艣艤艦艪艫艱艷艸芻苧茲荊莊莖莢莧華菴萇萊萬萵葉葒葠葤葦葯葷蒐蒓蒔蒞蒼蓀蓆蓋蓡蓧蓮蓯蓴蓽蔆蔔蔞蔣蔥蔦蔭蔴蕁蕆蕎蕒蕓蕕蕘蕢蕩蕪蕭蕷薈薊薌薑薔薘薟薦薩薺藍藎藝藥藪藶藹藺蘀蘄蘆蘇蘊蘋蘚蘞蘢蘭蘺蘿處虛虜號虧虯蛺蛻蜆蝕蝟蝦蝨蝸螄螘螞螢螻螿蟄蟈蟎蟣蟬蟯蟲蟶蟻蠅蠆蠍蠐蠑蠔蠟蠣蠨蠱蠶蠻衆衊術衚衛衝衞衹袞裊裌裏補裝裡製複褌褘褲褳褸褻襆襇襍襏襖襝襠襤襪襯襲襴覈見覎規覓覔視覘覜覡覥覦親覬覯覰覲覷覺覻覽覿觀觝觴觶觸訁訂訃計訊訌討訏訐訒訓訕訖託記訛訝訟訢訣訥訦訩訪設許訴訶診註証詁詆詎詐詒詔評詖詗詘詛詞詠詡詢詣試詩詫詬詭詮詰話該詳詵詼詾詿誄誅誆誇誌認誑誒誕誘誚語誠誡誣誤誥誦誨說説誰課誶誹誼調諂諄談諉請諍諏諑諒論諗諛諜諝諞諡諢諤諦諧諫諭諮諱諳諶諷諸諺諼諾謀謁謂謄謅謊謎謐謔謖謗謙謚講謝謠謨謫謬謳謹謾譁譆證譌譎譏譖識譙譚譜譟譫譭譯議譴護譸譽譾讀讁讅變讋讌讎讐讒讓讕讖讚讛讜讞谿豈豎豐豔豬豶貍貓貝貞貟負財貢貧貨販貪貫責貯貰貲貳貴貶買貸貺費貼貽貿賀賁賂賃賄賅資賈賉賊賍賑賒賓賔賕賙賚賛賜賞賠賡賢賣賤賦賧質賫賬賭賮賴賵賸賺賻購賽賾贄贅贇贈贊贋贍贏贐贓贔贖贗贛贜赬趕趙趨趲跡跼踐踡踴蹌蹕蹟蹠蹣蹤蹺躂躉躊躋躍躑躒躓躕躚躡躥躦躪躰軀軃車軋軌軍軑軒軔軛軟軤軫軲軸軹軺軻軼軾較輅輇輈載輊輒輓輔輕輛輜輝輞輟輥輦輩輪輬輭輯輳輸輻輼輾輿轀轂轄轅轆轉轍轎轔轟轡轢轤辤辦辭辮辯農辳迆迴迺逕這連週進遊運過達違遙遜遝遞遠適遲遷選遺遼邁還邇邊邏邐郃郟郤郵鄆鄉鄒鄔鄕鄖鄘鄧鄭鄰鄲鄴鄶鄺酈酧醃醖醜醞醫醬醱醻醼釀釁釃釅釋釐釓釔釕釗釘釙針釣釤釦釧釩釬釵釷釹釺鈀鈁鈃鈄鈅鈈鈉鈍鈎鈐鈑鈒鈔鈕鈞鈡鈣鈥鈦鈧鈮鈰鈳鈴鈷鈸鈹鈺鈽鈾鈿鉀鉄鉅鉆鉈鉉鉋鉍鉑鉕鉗鉚鉛鉞鉢鉤鉦鉬鉭鉲鉶鉸鉺鉻鉿銀銃銅銍銑銓銕銖銘銚銛銜銠銣銥銦銨銩銪銫銬銱銲銳銷銹銻銼鋁鋂鋃鋅鋇鋌鋏鋒鋙鋜鋝鋟鋣鋤鋥鋦鋨鋩鋪鋮鋯鋰鋱鋶鋸鋻鋼錁錄錆錇錈錏錐錒錕錘錙錚錛錟錠錡錢錦錨錩錫錮錯錳錶錸錼鍀鍁鍃鍆鍇鍈鍊鍋鍍鍔鍘鍚鍛鍠鍤鍥鍩鍫鍬鍰鍵鍶鍺鍼鍾鎂鎄鎇鎊鎋鎔鎖鎘鎚鎛鎡鎢鎣鎦鎧鎩鎪鎬鎮鎰鎲鎳鎵鎸鎿鏃鏇鏈鏌鏍鏐鏑鏗鏘鏚鏜鏝鏞鏟鏡鏢鏤鏨鏰鏵鏷鏹鏽鐃鐋鐐鐒鐓鐔鐘鐙鐝鐠鐦鐧鐨鐫鐮鐲鐳鐵鐶鐸鐺鐿鑄鑊鑌鑑鑒鑔鑕鑛鑞鑠鑣鑤鑥鑪鑭鑰鑱鑲鑷鑹鑼鑽鑾鑿钁钂長門閂閃閆閈閉開閌閎閏閑閒間閔閘閙閡関閣閤閥閨閩閫閬閭閱閲閶閹閻閼閽閾閿闃闆闈闊闋闌闍闐闒闓闔闕闖闚關闞闠闡闢闤闥阬阯陘陝陞陣陰陳陸陽隂隄隉隊階隕隖際隣隨險隱隴隷隸隻雋雖雙雛雜雞離難雲電霑霛霤霧霽靂靄靆靈靉靚靜靣靦靨靭鞀鞉鞏鞦鞽韁韃韆韉韋韌韍韓韙韜韝韞韤韻響頁頂頃項順頇須頊頌頎頏預頑頒頓頗領頜頡頤頦頫頭頮頰頲頴頷頸頹頻頽顆題額顎顏顒顓顔願顙顛類顢顥顧顫顬顯顰顱顳顴風颭颮颯颱颳颶颸颺颻颼飀飃飄飆飇飈飛飢飣飥飩飪飫飭飯飲飴飼飽飾飿餃餄餅餉養餌餎餏餑餒餓餕餖餘餚餛餜餞餡館餱餳餵餶餷餺餼餽餾餿饁饃饅饈饉饊饋饌饑饒饗饜饝饞饢馬馭馮馱馳馴馹駁駐駑駒駔駕駘駙駛駝駟駡駢駭駮駰駱駸駿騁騂騅騌騍騎騏騐騖騗騙騣騤騫騭騮騰騶騷騸騾驀驁驂驃驄驅驊驌驍驏驕驗驘驚驛驟驢驤驥驦驪驫骾髏髒體髕髖髩髮鬁鬆鬍鬚鬢鬥鬦鬧鬨鬩鬭鬮鬱魎魘魚魛魢魨魯魴魷魺鮁鮃鮊鮋鮌鮍鮎鮐鮑鮒鮓鮚鮜鮞鮦鮪鮫鮭鮮鮳鮶鮷鮺鯀鯁鯇鯉鯊鯒鯔鯕鯖鯗鯛鯝鯡鯢鯤鯧鯨鯪鯫鯰鯴鯵鯷鯽鯿鰁鰂鰃鰈鰉鰌鰍鰏鰐鰒鰓鰛鰜鰟鰠鰣鰥鰨鰩鰭鰮鰱鰲鰳鰵鰷鰹鰺鰻鰼鰾鱂鱅鱈鱉鱒鱓鱔鱖鱗鱘鱝鱟鱠鱣鱤鱧鱨鱭鱯鱷鱸鱺鳥鳧鳩鳲鳳鳴鳶鴆鴇鴉鴒鴕鴛鴝鴞鴟鴣鴦鴨鴬鴯鴰鴴鴻鴿鵂鵃鵐鵑鵒鵓鵜鵝鵞鵠鵡鵪鵬鵮鵯鵲鵶鵷鵾鶇鶉鶊鶓鶖鶘鶚鶡鶤鶥鶩鶬鶯鶲鶴鶹鶺鶻鶼鶿鷀鷁鷂鷄鷊鷓鷖鷗鷙鷚鷥鷦鷫鷯鷰鷲鷳鷴鷸鷹鷺鷼鸇鸌鸎鸏鸕鸘鸚鸛鸝鸞鹵鹹鹺鹼鹽麅麗麥麩麪麯麵麼黃黌點黨黲黴黶黷黽黿鼂鼇鼈鼉鼕鼴齇齊齋齎齏齒齔齕齗齙齜齟齠齡齣齦齧齪齬齲齶齷龍龐龔龕龜伋佪僇冑凈剎劻吋吶呎墑妳屝岧嵒巑庂廱弒戉扐扠扢扺抃抆抎抴挶搟撢晞暸朮杇枙毌汍浬牪瓖畬疿痲痳皻睪瞇瞭祅祇笻箄捍縳纔脧芐茍薧藷蘗襬觔著踫鎗鐉鐽阨陜骯鶱兀";
    private static final String GBK_T2S_S_MAP =
            "丢并乱亘亚夫伫布占并来仑侣局俣系侠伥俩仓个们幸仿伦伟侧侦偷咱伪杰伧伞备效家佣偬传伛债伤倾偻仅佥侨仆伪侥偾雇价仪侬亿当侩俭傧俦侪尽偿优储俪罗攒傩傥俨凶兑儿兖内两册幂涂冻凛渎处凯凭别删刭则克刬刚剥剐剀创铲划札剧刘刽刿剑剂剑劲动勖务勋胜劳势绩剿勚劢勋励劝匀匦汇匮奁奁椟区协恤却厍厅厕历厌厂厉厣参丛吴吕呙员咔呗唣吣念问启啖哑启唡衔咱唤岩丧吃乔单哟呛啬唝吗呜唢哔叹喽呕啧尝唛哗唠啸叽哓呒啴恶咴嘘咝哒哝哕嗳哙喷吨当咛吓哜尝噜啮咽呖咙向亸喾严嘤嚣啭嗫嚣冁呓啰苏嘱回囱回国囵国围园圆图团垧埯坝垭采执坚垩垴埚阶尧报场碱块茔垲埘涂冢坞埙场尘堑砖垫坠硗堕坛坟垯墙垦坛垱埙压垒圹垆坛坏垄垄坜坝塆壮壶壸寿寿够梦夹奂奥奁夺奋妆姗奸侄娱妩娄妇淫娅娲偷妫媪妈袅妪妩娴娴婳妫娆婵娇嫱袅嫒嬷嫔奶婴婶懒娘娈孙学孪宫寝实宁审写宽宠宝克将专寻对导尴尴届尸屃屉屡层屦属屃冈岘岛峡崃昆昆岗仑岽峥岽嵛岚岁嵝崭岖嵚崂峤峣峄峃崄岙嵘岭屿岳岿峦巅岩巯卺帅师帐带帧帏帼帻帜币帮帱襕干几库厕厢厩厦庼荫厨厮庙厂庑废广廪庐厅回乃吊弪张强别弹强弥弯汇彝彝彟彦雕佛径从徕复仿征彻恒耻悦怅闷凄恶恼恽恻爱惬悫怆恺忾栗殷态愠惨惭惭恸惯悫怄怂虑悭慑庆戚欲忧惫怜凭愦慭惮愤悯怃宪忆恳应怿懔恹蒙怼懑恹惩懒怀悬忏惧欢慑恋戆戋戗戬戏战戏户扦抛挟舍扪卷扫抡挜挣挂采拣扬换挥背构揿损摇捣抢榨掴掼搂揸挚抠抟掺捞挦撑挠捻挢掸拨抚扑揿挞挝捡拥掳择击挡担携据挤抬捣举拟摈拧搁掷扩撷摆擞撸扰摅撵拢拦撄搀撺携摄攒挛摊挡搅揽考叙败叙扬敌数驱敛毙斓斩断旗升时晋昼晕晖旸畅暂昵晔历昙晓向暧旷叠昽晒书会胧东拐栅拐杆栀枧条枭捆弃枨枣栋栈桊栖梾桠杨枫桢业极干杩荣桤盘构枪杠椠椁桨规桩乐枞梁楼标枢样丛朴树桦桡桥机椭横槔檩柽档桧槚检樯梼台槟柠槛苘棹柜橹榈栉椟橼栎橱槠栌枥橥榇栊榉棂樱栏权椤攒栾榄棂钦叹欧啸敛欤欢岁历归殁残殒殇殚殓殡歼杀壳毁殴医绒球毵牦毡毡氇气氢氩氲凼泛泛污污决冱没冲况泄汹浃泾莅凉凄泪渌净凌沦渊涞浅涣减沨涡测浑凑浈愍涌汤沩准沟温浉涢湿沧灭涤荥汇沪滞渗卤浒浐滚满渔溇沤汉涟渍涨溆渐浆颍泼洁沩潜舄润浔溃滗涠涩涩澄浇涝涧渑泽滪泶浍淀浊浓沵湿泞蒙浕济涛滥浚潍滨阔溅泺滤滢渎泻渖浏濒泸沥潇潆潴泷濑弥潋澜沣滠洒漓滩灏湾滦滟滟灾照为乌烃无煅辉炼炜暖烟茕焕烦炀荧炝热颎炽烨焰灯炖磷烧烫焖营灿毁烛烩熏烬焘耀烁炉烨烂争为爷尔床墙笺闸牍抵牵荦牦犊牺状狭狈狰犹狲犸呆狱狮奖独狯猃狝狞获猎犷兽獭献猕猡兹珏佩现璃管珐珲珉玮玚琐瑶莹玛琅玱琏琎玑瑷珰环玙玺璇琼珑璎瓒瓯砖瓮罂产产苏亩毕画异留当畴迭痉酸痹痖愈疯疡痪瘗疮疟瘆瘘瘘疗痨痫瘅愈疠瘪痴痒疖症疬癞癣瘿瘾痈瘫癫发皑疱皲皱杯盗盏尽监盘卢荡视视眦众困睁睐眍瞒睑蒙眬瞩矫炮朱硁硖砗砚碜硕砀砜确码硙砖碜碛矶硗硚础碍矿砺砾矾炮砻佑秘禄祸祯祎御禅礼祢祷秃籼税秆棱禀秸种称谷稣积颖秾穑秽颓稳获稆窝洼穷窑窑窭窥窜窍窦灶窃并竖竞笔笋笕个笺筝棰节范筑箧箬筱笃筛筚箦篓蓑箪简篑箫檐筜签帘篮筹藤箓箨籁笼奁签钥笾簖篱箩吁妆曲粤糁粪模粮团粝籴粜纠纪纣约红纡纥纨纫纹纳纽纾纯纰纼纱纮纸级纷纭纴纺扎扎细绂绁绅纻绍绀绋绐绌终弦组绊纴绗绁结绝绦绔绞络绚给绒绖统丝绛绢绑绡绠绨绣绤绥捆经综缍绿绸绻线绶维绹绾纲网绷缀彩纶绺绮绽绰绫绵绲缁紧绯缗绪绬绱缃缄缂线绵缉缎缔缗缘绁缌编缓缅纬缑缈练缏缇致缊总萦缙缢缒绉缣缊缞绦缚缜缟缛县绦缝缡缩演纵缧纤缦絷缕缥总绩绷缫缪襁缯织缮伞翻缭绕绣缋襁绳绘系茧缰缳缲缴绎继缤缱颣缬纩续累缠缨纤缵缆钵坛瓮罂坛垆罚骂罢罚罗罴羁芈绒羟羡义膻习翚翘翱翙端锄耧耢圣闻联聪声耸聩聂职聍听听聋肃胁胁脉胫唇修脱胀肾胨脶脑肿脚肠肷腽腘肤胶腻胆脍脓脸脐膑癯腊胪脏脔臜卧临皋台与兴举旧铺馆舱樯橹舣舰橹舻艰艳艹刍苎兹荆庄茎荚苋华庵苌莱万莴叶荭参荮苇药荤搜莼莳莅苍荪席盖参莜莲苁莼荜菱卜蒌蒋葱茑荫麻荨蒇荞荬芸莸荛蒉荡芜萧蓣荟蓟芗姜蔷荙莶荐萨荠蓝荩艺药薮苈蔼蔺萚蕲芦苏蕴苹藓蔹茏兰蓠萝处虚虏号亏虬蛱蜕蚬蚀猬虾虱蜗蛳蚁蚂萤蝼螀蛰蝈螨虮蝉蛲虫蛏蚁蝇虿蝎蛴蝾蚝蜡蛎蟏蛊蚕蛮众蔑术胡卫冲卫只衮袅夹里补装里制复裈袆裤裢褛亵幞裥杂袯袄裣裆褴袜衬袭襕核见觃规觅觅视觇眺觋觍觎亲觊觏觑觐觑觉觑览觌观抵觞觯触讠订讣计讯讧讨吁讦讱训讪讫托记讹讶讼欣诀讷谌讻访设许诉诃诊注证诂诋讵诈诒诏评诐诇诎诅词咏诩询诣试诗诧诟诡诠诘话该详诜诙讻诖诔诛诓夸志认诳诶诞诱诮语诚诫诬误诰诵诲说说谁课谇诽谊调谄谆谈诿请诤诹诼谅论谂谀谍谞谝谥诨谔谛谐谏谕谘讳谙谌讽诸谚谖诺谋谒谓誊诌谎谜谧谑谡谤谦谥讲谢谣谟谪谬讴谨谩哗嘻证讹谲讥谮识谯谭谱噪谵毁译议谴护诪誉谫读谪审变詟燕雠雠谗让谰谶赞呓谠谳溪岂竖丰艳猪豮狸猫贝贞贠负财贡贫货贩贪贯责贮贳赀贰贵贬买贷贶费贴贻贸贺贲赂赁贿赅资贾恤贼赃赈赊宾宾赇赒赉赞赐赏赔赓贤卖贱赋赕质赍账赌赆赖赗剩赚赙购赛赜贽赘赟赠赞赝赡赢赆赃赑赎赝赣赃赪赶赵趋趱迹局践蜷踊跄跸迹跖蹒踪跷跶趸踌跻跃踯跞踬蹰跹蹑蹿躜躏体躯亸车轧轨军轪轩轫轭软轷轸轱轴轵轺轲轶轼较辂辁辀载轾辄挽辅轻辆辎辉辋辍辊辇辈轮辌软辑辏输辐辒辗舆辒毂辖辕辘转辙轿辚轰辔轹轳辞办辞辫辩农农迤回乃径这连周进游运过达违遥逊沓递远适迟迁选遗辽迈还迩边逻逦合郏郄邮郓乡邹邬乡郧墉邓郑邻郸邺郐邝郦酬腌酝丑酝医酱酦酬燕酿衅酾酽释厘钆钇钌钊钉钋针钓钐扣钏钒焊钗钍钕钎钯钫钘钭钥钚钠钝钩钤钣钑钞钮钧钟钙钬钛钪铌铈钶铃钴钹铍钰钸铀钿钾铁巨钻铊铉刨铋铂钷钳铆铅钺钵钩钲钼钽锎铏铰铒铬铪银铳铜铚铣铨铁铢铭铫铦衔铑铷铱铟铵铥铕铯铐铞焊锐销锈锑锉铝镅锒锌钡铤铗锋铻镯锊锓铘锄锃锔锇铓铺铖锆锂铽锍锯鉴钢锞录锖锫锩铔锥锕锟锤锱铮锛锬锭锜钱锦锚锠锡锢错锰表铼镎锝锨锪钔锴锳炼锅镀锷铡钖锻锽锸锲锘锹锹锾键锶锗针锺镁锿镅镑辖镕锁镉锤镈镃钨蓥镏铠铩锼镐镇镒镋镍镓镌镎镞旋链镆镙镠镝铿锵戚镗镘镛铲镜镖镂錾镚铧镤镪锈铙铴镣铹镦镡钟镫镢镨锎锏镄镌镰镯镭铁镮铎铛镱铸镬镔鉴鉴镲锧矿镴铄镳刨镥炉镧钥镵镶镊镩锣钻銮凿镢镋长门闩闪闫闬闭开闶闳闰闲闲间闵闸闹阂关阁合阀闺闽阃阆闾阅阅阊阉阎阏阍阈阌阒板闱阔阕阑阇阗阘闿阖阙闯窥关阚阓阐辟阛闼坑址陉陕升阵阴陈陆阳阴堤陧队阶陨坞际邻随险隐陇隶隶只隽虽双雏杂鸡离难云电沾灵溜雾霁雳霭叇灵叆靓静面腼靥韧鼗鼗巩秋鞒缰鞑千鞯韦韧韨韩韪韬鞴韫袜韵响页顶顷项顺顸须顼颂颀颃预顽颁顿颇领颌颉颐颏俯头颒颊颋颕颔颈颓频颓颗题额颚颜颙颛颜愿颡颠类颟颢顾颤颥显颦颅颞颧风飐飑飒台刮飓飔扬飖飕飗飘飘飙飙飙飞饥饤饦饨饪饫饬饭饮饴饲饱饰饳饺饸饼饷养饵饹饻饽馁饿馂饾余肴馄馃饯馅馆糇饧喂馉馇馎饩馈馏馊馌馍馒馐馑馓馈馔饥饶飨餍馍馋馕马驭冯驮驰驯驲驳驻驽驹驵驾骀驸驶驼驷骂骈骇驳骃骆骎骏骋骍骓骔骒骑骐验骛骗骗鬃骙骞骘骝腾驺骚骟骡蓦骜骖骠骢驱骅骕骁骣骄验骡惊驿骤驴骧骥骦骊骉鲠髅脏体髌髋鬓发疬松胡须鬓斗斗闹哄阋斗阄郁魉魇鱼鱽鱾鲀鲁鲂鱿鲄鲅鲆鲌鲉鲧鲏鲇鲐鲍鲋鲊鲒鲘鲕鲖鲔鲛鲑鲜鲓鲪鳀鲝鲧鲠鲩鲤鲨鲬鲻鲯鲭鲞鲷鲴鲱鲵鲲鲳鲸鲮鲰鲶鲺鲹鳀鲫鳊鳈鲗鳂鲽鳇鳅鳅鲾鳄鳆鳃鳁鳒鳑鳋鲥鳏鳎鳐鳍鳁鲢鳌鳓鳘鲦鲣鲹鳗鳛鳔鳉鳙鳕鳖鳟鳝鳝鳜鳞鲟鲼鲎鲙鳣鳡鳢鲿鲚鳠鳄鲈鲡鸟凫鸠鸤凤鸣鸢鸩鸨鸦鸰鸵鸳鸲鸮鸱鸪鸯鸭鸴鸸鸹鸻鸿鸽鸺鸼鹀鹃鹆鹁鹈鹅鹅鹄鹉鹌鹏鹐鹎鹊鸦鹓鹍鸫鹑鹒鹋鹙鹕鹗鹖鹍鹛鹜鸧莺鹟鹤鹠鹡鹘鹣鹚鹚鹢鹞鸡鹝鹧鹥鸥鸷鹨鸶鹪鹔鹩燕鹫鹇鹇鹬鹰鹭鹇鹯鹱莺鹲鸬鹴鹦鹳鹂鸾卤咸鹾碱盐狍丽麦麸面曲面么黄黉点党黪霉黡黩黾鼋鼌鳌鳖鼍冬鼹齄齐斋赍齑齿龀龁龂龅龇龃龆龄出龈啮龊龉龋腭龌龙庞龚龛龟汲徊戮胄净刹匡寸呐尺墒你扉迢岩漓仄痈弑钺仂叉扦抵拚擦殒曳局擀掸曦了术圬栀毋丸里瘪镶畲痱麻麻隳睾眯了祆只筇箅扞缚才睃苄苟槁薯蘖摆筋着碰枪铣钽厄陕肮骞兀";
    private static final String GBK_S2T_S_MAP =
            "皑蔼碍爱袄奥坝罢摆败颁办绊帮绑镑谤剥饱宝报鲍辈贝钡狈备惫绷笔毕毙币闭边编贬变辩辫标鳖别瘪濒滨宾摈饼并拨钵铂驳卜补财参蚕残惭惨灿苍舱仓沧厕侧册测层诧搀掺蝉馋谗缠铲产阐颤场尝长偿肠厂畅钞车彻尘陈衬撑称惩诚骋痴迟驰耻齿炽冲虫宠畴踌筹绸丑橱厨锄雏础储触处传疮闯创锤纯绰辞词赐聪葱囱从丛凑蹿窜错达带贷担单郸掸胆惮诞弹当挡党荡档捣岛祷导盗灯邓敌涤递缔颠点垫电淀钓调谍叠钉顶锭订丢东动栋冻斗犊独读赌镀锻断缎兑队对吨顿钝夺堕鹅额讹恶饿儿尔饵贰发罚阀珐矾钒烦范贩饭访纺飞诽废费纷坟奋愤粪丰枫锋风疯冯缝讽凤肤辐抚辅赋复负讣妇缚该钙盖干赶秆赣冈刚钢纲岗镐搁鸽阁铬个给龚宫巩贡钩沟构购够蛊谷顾剐挂关观馆惯贯广规硅归龟闺轨诡柜贵刽辊滚锅国过骇韩汉号阂鹤贺横轰鸿红后壶护沪户哗华画划话怀坏欢环还缓换唤痪焕涣黄谎挥辉毁贿秽会烩汇讳诲绘荤浑伙获货祸击机积饥迹讥鸡绩缉极辑级挤几蓟剂济计记际继纪夹荚颊贾钾价驾歼监坚笺间艰缄茧检碱硷拣捡简俭减荐槛鉴践贱见键舰剑饯渐溅涧姜将浆蒋桨奖讲酱胶浇骄娇搅铰矫侥脚饺缴绞轿较阶节杰洁结诫届紧锦仅谨进晋烬尽劲荆茎鲸惊经颈静镜径痉竞净纠厩旧驹举据锯惧剧鹃绢觉决诀绝钧军骏开凯颗壳课垦恳抠库裤夸块侩宽矿旷况亏岿窥馈溃扩阔蜡腊莱来赖蓝栏拦篮阑兰澜谰揽览懒缆烂滥捞劳涝乐镭垒类泪篱离里鲤礼丽厉励砾历沥隶俩联莲连镰怜涟帘敛脸链恋炼练粮凉两辆谅疗辽镣猎临邻鳞凛赁龄铃凌灵岭领馏刘龙聋咙笼垄拢陇楼娄搂篓芦卢颅庐炉掳卤虏鲁赂禄录陆驴吕铝侣屡缕虑滤绿峦挛孪滦乱抡轮伦仑沦纶论萝罗逻锣箩骡骆络妈玛码蚂马骂吗买麦卖迈脉瞒馒蛮满谩猫锚铆贸么霉没镁门闷们锰梦谜弥觅幂绵缅庙灭悯闽鸣铭谬谋亩钠纳难挠脑恼闹馁内拟腻撵捻酿鸟聂啮镊镍柠狞宁拧泞钮纽脓浓农疟诺欧鸥殴呕沤盘庞抛赔喷鹏骗飘频贫苹凭评泼颇扑铺仆朴谱栖凄脐齐骑岂启气弃讫牵扦钎铅迁签谦钱钳潜浅谴堑枪呛墙蔷强抢锹桥乔侨翘窍窃钦亲寝轻氢倾顷请庆琼穷趋区躯驱龋颧权劝却鹊确让饶扰绕热韧认纫荣绒软锐闰润洒萨鳃赛叁伞丧骚扫涩杀纱筛晒删闪陕赡缮伤赏烧绍赊摄慑设绅审婶肾渗声绳胜圣师狮湿诗尸时蚀实识驶势适释饰视试寿兽枢输书赎属术树竖数帅双谁税顺说硕烁丝饲耸怂颂讼诵擞苏诉肃虽随绥岁孙损笋缩琐锁獭挞态摊贪瘫滩坛谭谈叹汤烫涛绦讨腾誊锑题体屉条贴铁厅听烃铜统头秃图涂团颓蜕脱鸵驮驼椭洼袜弯湾顽万网韦违围为潍维苇伟伪纬谓卫温闻纹稳问瓮挝蜗涡窝卧呜钨乌污诬无芜吴坞雾务误锡牺袭习铣戏细虾辖峡侠狭厦吓锨鲜纤咸贤衔闲显险现献县馅羡宪线厢镶乡详响项萧嚣销晓啸蝎协挟携胁谐写泻谢锌衅兴汹锈绣虚嘘须许叙绪续轩悬选癣绚学勋询寻驯训讯逊压鸦鸭哑亚讶阉烟盐严颜阎艳厌砚彦谚验鸯杨扬疡阳痒养样瑶摇尧遥窑谣药爷页业叶医铱颐遗仪蚁艺亿忆义诣议谊译异绎荫阴银饮隐樱婴鹰应缨莹萤营荧蝇赢颖哟拥佣痈踊咏涌优忧邮铀犹游诱于舆鱼渔娱与屿语郁吁御狱誉预驭鸳渊辕园员圆缘远愿约跃钥岳粤悦阅云郧匀陨运蕴酝晕韵杂灾载攒暂赞赃脏凿枣责择则泽贼赠扎札轧铡闸栅诈斋债毡盏斩辗崭栈战绽张涨帐账胀赵蛰辙锗这贞针侦诊镇阵挣睁狰争帧郑证织职执纸挚掷帜质滞钟终种肿众诌轴皱昼骤猪诸诛烛瞩嘱贮铸筑驻专砖转赚桩庄装妆壮状锥赘坠缀谆着浊兹资渍踪综总纵邹诅组钻亘鼗芈啬厍厣厮靥赝匦匮赜刭刿剀伛伥伧伫侪侬俦俨俪俣偾偬偻傥傧傩佥籴黉冁凫兖衮亵脔禀冢讠讦讧讪讴讵讷诂诃诋诏诎诒诓诔诖诘诙诜诟诠诤诨诩诮诰诳诶诹诼诿谀谂谄谇谌谏谑谒谔谕谖谙谛谘谝谟谠谡谥谧谪谫谮谯谲谳谵谶卺陉陧邝邬邺郏郐郄郓郦刍奂劢勐凼巯垩圹坜坂垅垆垭垲垧垴埘埚埙埯埝塬艹芗苈苋苌苁苎茏苘茑茔茕荛荜荞荟荠荦荥荨荩荬荪荭荮莳莴莜莅莶莸莺莼萦蒇蒉蒌蓦蓠蓥蓣蔹蔺蕲薮藓藁奁尴扪抟挢掴掼揸揿摅撄撷撸撺叽呒呓呖呗呙吣咔咛咝咴哒哓哔哕哌哙哜咤哝唛唠唢唣啧啭唿喽喾嗫嗬嗳辔嘤噜噼嚯囵帏帱帻帼幞岖岘岙岚岽峄峤峥崂崃嵘嵛嵝嵴巅徕犭犷犸狍狯狲猃猡猕猬饧饨饩饪饫饬饴饷饽馀馄馇馊馍馐馑馓馔馕庑赓廪忏怃怄忾怅怆怿恸恹恻恺恽悭惬愠愦憷懔闩闫闱闳闵闶闼闾阃阄阆阈阊阋阌阍阏阒阕阖阗阙阚丬沣沩泷泸泺泾浃浈浍浏浒浔涞涠渎渑渖渌溆滟滠滢滗潆潇漤潋潴濑灏骞迩迳逦屦弪妩妪妫姗娅娆娈娲娴婵媪嫒嫔嫱嬷驵驷驸驺驿驽骀骁骅骈骊骐骒骓骖骘骛骜骝骟骠骢骣骥骧纟纡纣纥纨纩纭纰纾绀绁绂绉绋绌绐绔绗绛绠绡绨绫绮绯绱绲缍绶绺绻绾缁缂缃缇缈缋缌缏缑缒缗缙缜缛缟缡缢缣缤缥缦缧缪缫缬缭缯缰缱缲缳缵幺玑玮珏珑珉顼玺珲琏瑷璎璇瓒韪韫韬杩枥枧枨枞枭栉栊栌栀栎柽桠桡桢桤桦桧栾桊棂椟椠椤椁榄榇榈榉槟槠樯橥橹橼檐檩殁殇殒殓殚殡轫轭轱轲轳轵轶轸轷轹轺轼轾辁辂辄辇辋辍辎辏辘辚戋戗戬瓯昙晔晖暧贲贳贶贻贽赀赅赆赈赉赇赍赕赙觇觊觋觌觎觏觐觑犟牦毵氇氩氲牍肷胧胨胪胫脍脶腌腼腽腭膑欤飑飒飓飕飙飚毂齑斓炀炜炖炝烨焖煳煅煺熘焘祢祯禅怼悫愍懑戆沓泶矶砀砗砜砺砻硖硗碛碜碹磙龛眍眦睐睑畲罴羁钆钇钋钊钌钍钏钐钔钗钕钚钛钣钤钫钪钭钬钯钰钲钴钶钷钸钹钺钼钽钿铄铈铉铊铋铌铍铎铐铑铒铕铖铗铙铘铛铞铟铠铢铤铥铧铨铪铩铫铮铯铳铴铵铷铹铼铽铿锃锂锆锇锉锊锍锎锏锒锓锔锕锖锘锛锝锞锟锢锪锫锩锬锱锲锴锶锷锸锼锾锿镂锵镄镅镆镉镌镎镏镒镓镔镖镗镘镙镛镞镟镝镡镢镤镥镦镧镨镩镪镫镬镯镱镲镳锺稆穑鸠鸢鸨鸩鸪鸫鸬鸲鸱鸶鸸鸷鸹鸺鸾鹁鹂鹄鹆鹇鹈鹉鹋鹌鹎鹑鹕鹗鹚鹛鹜鹞鹣鹦鹧鹨鹩鹪鹫鹬鹱鹭鹳疖疠疬疴疱痖痨痫瘅瘗瘘瘿瘾癞癫癯窦窭裆裢裣裥褛褴襁皲耢耧聍聩顸颀颃颉颌颍颏颔颚颛颞颟颡颢颥颦虬虮虿蚬蚝蛎蛏蛱蛲蛳蛴蝈蝾蝼螨罂笃笕笾筚筝箦箧箨箪箫篑簖籁舣舻袅羟籼粝粜糁糇絷麸趱酰酽酾鹾趸跄跖跞跷跸跹跻踬踯蹑蹒蹰躏躜觞觯靓雳霁霭龀龃龅龆龇龈龉龊龌黾鼋鼍隽雠銮錾鱿鲂鲅鲆鲇鲈稣鲋鲎鲐鲑鲒鲔鲕鲚鲛鲞鲟鲠鲡鲢鲣鲥鲦鲧鲨鲩鲫鲭鲮鲰鲱鲲鲳鲴鲵鲶鲷鲺鲻鲼鲽鳄鳅鳆鳇鳊鳋鳌鳍鳎鳏鳐鳓鳔鳕鳗鳘鳙鳜鳝鳟鳢鞑鞒鞯鞴鹘髅髋髌魇魉飨餍鬓黩黪鼹齄亸刬勚叆叇唝唡啰啴垯垱塆壸婳屃峃峣崄嵚庼彟慭挜挦旸昽梼梾槚沨沵浉浐浕涢溇滪狝玙玚玱珰琎瘆眬硁硙硚祎秾筜箓纮纴纻纼绖绤绬绹缊缐缞翙翚腘臜荙萚螀蟏袆袯裈襕觃觍詟讱讻诇诐诪谞豮贠赑赒赗赟赪跶轪辀辌辒酦鉅钑钖钘铏铓铔铚铦铻锜锠锧锳锽镃镈镋镕镚镠镮镴镵闬闿阇阓阘阛韨颋颎颒颕颙颣飏飐飔飖飗饤饦饳饸饹饻饾馂馃馉馌馎驲骃骉骍骎骔骕骙骦鱽鱾鲀鲄鲉鲊鲌鲏鲓鲖鲗鲘鲙鲝鲪鲬鲯鲹鲾鲿鳀鳁鳂鳈鳉鳑鳒鳛鳠鳡鳣鸤鸧鸮鸰鸴鸻鸼鹀鹍鹐鹒鹓鹔鹖鹙鹝鹟鹠鹡鹢鹥鹯鹲鹴黡鼌龁龂扞肮杆苟琅狸眯呐刹墒台凶岩症准睾芸苄蘖擀弑睃钜麽";
    private static final String GBK_S2T_T_MAP =
            "皚藹礙愛襖奧壩罷擺敗頒辦絆幫綁鎊謗剝飽寶報鮑輩貝鋇狽備憊繃筆畢斃幣閉邊編貶變辯辮標鱉別癟瀕濱賓擯餅並撥缽鉑駁蔔補財參蠶殘慚慘燦蒼艙倉滄廁側冊測層詫攙摻蟬饞讒纏鏟產闡顫場嘗長償腸廠暢鈔車徹塵陳襯撐稱懲誠騁癡遲馳恥齒熾沖蟲寵疇躊籌綢醜櫥廚鋤雛礎儲觸處傳瘡闖創錘純綽辭詞賜聰蔥囪從叢湊躥竄錯達帶貸擔單鄲撣膽憚誕彈當擋黨蕩檔搗島禱導盜燈鄧敵滌遞締顛點墊電澱釣調諜疊釘頂錠訂丟東動棟凍鬥犢獨讀賭鍍鍛斷緞兌隊對噸頓鈍奪墮鵝額訛惡餓兒爾餌貳發罰閥琺礬釩煩範販飯訪紡飛誹廢費紛墳奮憤糞豐楓鋒風瘋馮縫諷鳳膚輻撫輔賦複負訃婦縛該鈣蓋幹趕稈贛岡剛鋼綱崗鎬擱鴿閣鉻個給龔宮鞏貢鉤溝構購夠蠱穀顧剮掛關觀館慣貫廣規矽歸龜閨軌詭櫃貴劊輥滾鍋國過駭韓漢號閡鶴賀橫轟鴻紅後壺護滬戶嘩華畫劃話懷壞歡環還緩換喚瘓煥渙黃謊揮輝毀賄穢會燴匯諱誨繪葷渾夥獲貨禍擊機積饑跡譏雞績緝極輯級擠幾薊劑濟計記際繼紀夾莢頰賈鉀價駕殲監堅箋間艱緘繭檢堿鹼揀撿簡儉減薦檻鑒踐賤見鍵艦劍餞漸濺澗薑將漿蔣槳獎講醬膠澆驕嬌攪鉸矯僥腳餃繳絞轎較階節傑潔結誡屆緊錦僅謹進晉燼盡勁荊莖鯨驚經頸靜鏡徑痙競淨糾廄舊駒舉據鋸懼劇鵑絹覺決訣絕鈞軍駿開凱顆殼課墾懇摳庫褲誇塊儈寬礦曠況虧巋窺饋潰擴闊蠟臘萊來賴藍欄攔籃闌蘭瀾讕攬覽懶纜爛濫撈勞澇樂鐳壘類淚籬離裏鯉禮麗厲勵礫曆瀝隸倆聯蓮連鐮憐漣簾斂臉鏈戀煉練糧涼兩輛諒療遼鐐獵臨鄰鱗凜賃齡鈴淩靈嶺領餾劉龍聾嚨籠壟攏隴樓婁摟簍蘆盧顱廬爐擄鹵虜魯賂祿錄陸驢呂鋁侶屢縷慮濾綠巒攣孿灤亂掄輪倫侖淪綸論蘿羅邏鑼籮騾駱絡媽瑪碼螞馬罵嗎買麥賣邁脈瞞饅蠻滿謾貓錨鉚貿麼黴沒鎂門悶們錳夢謎彌覓冪綿緬廟滅憫閩鳴銘謬謀畝鈉納難撓腦惱鬧餒內擬膩攆撚釀鳥聶齧鑷鎳檸獰寧擰濘鈕紐膿濃農瘧諾歐鷗毆嘔漚盤龐拋賠噴鵬騙飄頻貧蘋憑評潑頗撲鋪僕樸譜棲淒臍齊騎豈啟氣棄訖牽扡釺鉛遷簽謙錢鉗潛淺譴塹槍嗆牆薔強搶鍬橋喬僑翹竅竊欽親寢輕氫傾頃請慶瓊窮趨區軀驅齲顴權勸卻鵲確讓饒擾繞熱韌認紉榮絨軟銳閏潤灑薩鰓賽三傘喪騷掃澀殺紗篩曬刪閃陝贍繕傷賞燒紹賒攝懾設紳審嬸腎滲聲繩勝聖師獅濕詩屍時蝕實識駛勢適釋飾視試壽獸樞輸書贖屬術樹豎數帥雙誰稅順說碩爍絲飼聳慫頌訟誦擻蘇訴肅雖隨綏歲孫損筍縮瑣鎖獺撻態攤貪癱灘壇譚談歎湯燙濤絛討騰謄銻題體屜條貼鐵廳聽烴銅統頭禿圖塗團頹蛻脫鴕馱駝橢窪襪彎灣頑萬網韋違圍為濰維葦偉偽緯謂衛溫聞紋穩問甕撾蝸渦窩臥嗚鎢烏汙誣無蕪吳塢霧務誤錫犧襲習銑戲細蝦轄峽俠狹廈嚇鍁鮮纖鹹賢銜閑顯險現獻縣餡羨憲線廂鑲鄉詳響項蕭囂銷曉嘯蠍協挾攜脅諧寫瀉謝鋅釁興洶鏽繡虛噓須許敘緒續軒懸選癬絢學勳詢尋馴訓訊遜壓鴉鴨啞亞訝閹煙鹽嚴顏閻豔厭硯彥諺驗鴦楊揚瘍陽癢養樣瑤搖堯遙窯謠藥爺頁業葉醫銥頤遺儀蟻藝億憶義詣議誼譯異繹蔭陰銀飲隱櫻嬰鷹應纓瑩螢營熒蠅贏穎喲擁傭癰踴詠湧優憂郵鈾猶遊誘於輿魚漁娛與嶼語鬱籲禦獄譽預馭鴛淵轅園員圓緣遠願約躍鑰嶽粵悅閱雲鄖勻隕運蘊醞暈韻雜災載攢暫贊贓髒鑿棗責擇則澤賊贈紮劄軋鍘閘柵詐齋債氈盞斬輾嶄棧戰綻張漲帳賬脹趙蟄轍鍺這貞針偵診鎮陣掙睜猙爭幀鄭證織職執紙摯擲幟質滯鐘終種腫眾謅軸皺晝驟豬諸誅燭矚囑貯鑄築駐專磚轉賺樁莊裝妝壯狀錐贅墜綴諄著濁茲資漬蹤綜總縱鄒詛組鑽亙鞀羋嗇厙厴廝靨贗匭匱賾剄劌剴傴倀傖佇儕儂儔儼儷俁僨傯僂儻儐儺僉糴黌囅鳧兗袞褻臠稟塚訁訐訌訕謳詎訥詁訶詆詔詘詒誆誄詿詰詼詵詬詮諍諢詡誚誥誑誒諏諑諉諛諗諂誶諶諫謔謁諤諭諼諳諦諮諞謨讜謖諡謐謫譾譖譙譎讞譫讖巹陘隉鄺鄔鄴郟鄶郤鄆酈芻奐勱猛氹巰堊壙壢阪壟壚埡塏坰堖塒堝塤垵墊原艸薌藶莧萇蓯苧蘢檾蔦塋煢蕘蓽蕎薈薺犖滎蕁藎蕒蓀葒葤蒔萵蓧蒞薟蕕鶯蓴縈蕆蕢蔞驀蘺鎣蕷蘞藺蘄藪蘚槁奩尷捫摶撟摑摜摣撳攄攖擷擼攛嘰嘸囈嚦唄咼唚哢嚀噝噅噠嘵嗶噦呱噲嚌吒噥嘜嘮嗩唕嘖囀呼嘍嚳囁呵噯轡嚶嚕劈謔圇幃幬幘幗襆嶇峴嶴嵐崠嶧嶠崢嶗崍嶸崳嶁脊巔徠犬獷獁麅獪猻獫玀獼蝟餳飩餼飪飫飭飴餉餑餘餛餷餿饃饈饉饊饌饢廡賡廩懺憮慪愾悵愴懌慟懨惻愷惲慳愜慍憒怵懍閂閆闈閎閔閌闥閭閫鬮閬閾閶鬩閿閽閼闃闋闔闐闕闞爿灃溈瀧瀘濼涇浹湞澮瀏滸潯淶潿瀆澠瀋淥漵灩灄瀅潷瀠瀟濫瀲瀦瀨灝騫邇逕邐屨弳嫵嫗媯姍婭嬈孌媧嫻嬋媼嬡嬪嬙嬤駔駟駙騶驛駑駘驍驊駢驪騏騍騅驂騭騖驁騮騸驃驄驏驥驤糸紆紂紇紈纊紜紕紓紺絏紱縐紼絀紿絝絎絳綆綃綈綾綺緋緔緄綞綬綹綣綰緇緙緗緹緲繢緦緶緱縋緡縉縝縟縞縭縊縑繽縹縵縲繆繅纈繚繒韁繾繰繯纘么璣瑋玨瓏瑉頊璽琿璉璦瓔璿瓚韙韞韜榪櫪梘棖樅梟櫛櫳櫨梔櫟檉椏橈楨榿樺檜欒棬欞櫝槧欏槨欖櫬櫚櫸檳櫧檣櫫櫓櫞簷檁歿殤殞殮殫殯軔軛軲軻轤軹軼軫軤轢軺軾輊輇輅輒輦輞輟輜輳轆轔戔戧戩甌曇曄暉曖賁貰貺貽贄貲賅贐賑賚賕齎賧賻覘覬覡覿覦覯覲覷強犛毿氌氬氳牘膁朧腖臚脛膾腡醃靦膃齶臏歟颮颯颶颼飆飆轂齏斕煬煒燉熗燁燜糊煆退溜燾禰禎禪懟愨湣懣戇遝澩磯碭硨碸礪礱硤磽磧磣镟滾龕瞘眥睞瞼佘羆羈釓釔釙釗釕釷釧釤鍆釵釹鈈鈦鈑鈐鈁鈧鈄鈥鈀鈺鉦鈷鈳鉕鈽鈸鉞鉬鉭鈿鑠鈰鉉鉈鉍鈮鈹鐸銬銠鉺銪鋮鋏鐃鋣鐺銱銦鎧銖鋌銩鏵銓鉿鎩銚錚銫銃鐋銨銣鐒錸鋱鏗鋥鋰鋯鋨銼鋝鋶鉲鐧鋃鋟鋦錒錆鍩錛鍀錁錕錮鍃錇錈錟錙鍥鍇鍶鍔鍤鎪鍰鎄鏤鏘鐨鋂鏌鎘鐫錼鎦鎰鎵鑌鏢鏜鏝鏍鏞鏃鏇鏑鐔钁鏷鑥鐓鑭鐠鑹鏹鐙鑊鐲鐿鑔鑣鍾穭穡鳩鳶鴇鴆鴣鶇鸕鴝鴟鷥鴯鷙鴰鵂鸞鵓鸝鵠鵒鷳鵜鵡鶓鵪鵯鶉鶘鶚鶿鶥鶩鷂鶼鸚鷓鷚鷯鷦鷲鷸鸌鷺鸛癤癘鬁屙皰瘂癆癇癉瘞瘺癭癮癩癲臒竇窶襠褳襝襇褸襤繈皸耮耬聹聵頇頎頏頡頜潁頦頷顎顓顳顢顙顥顬顰虯蟣蠆蜆蠔蠣蟶蛺蟯螄蠐蟈蠑螻蟎罌篤筧籩篳箏簀篋籜簞簫簣籪籟艤艫嫋羥秈糲糶糝餱縶麩趲醯釅釃鹺躉蹌蹠躒蹺蹕躚躋躓躑躡蹣躕躪躦觴觶靚靂霽靄齔齟齙齠齜齦齬齪齷黽黿鼉雋讎鑾鏨魷魴鮁鮃鯰鱸穌鮒鱟鮐鮭鮚鮪鮞鱭鮫鯗鱘鯁鱺鰱鰹鰣鰷鯀鯊鯇鯽鯖鯪鯫鯡鯤鯧鯝鯢鯰鯛鯴鯔鱝鰈鱷鰍鰒鰉鯿鰠鼇鰭鰨鰥鰩鰳鰾鱈鰻鰵鱅鱖鱔鱒鱧韃鞽韉韝鶻髏髖髕魘魎饗饜鬢黷黲鼴齇嚲剗勩靉靆嗊啢囉嘽墶壋壪壼嫿屭嶨嶢嶮嶔廎彠憖掗撏暘曨檮棶檟渢濔溮滻濜溳漊澦獮璵瑒瑲璫璡瘮矓硜磑礄禕穠簹籙紘紝紵紖絰綌緓綯縕線縗翽翬膕臢薘蘀螿蠨褘襏褌襴覎覥讋訒訩詗詖譸諝豶貟贔賙賵贇赬躂軑輈輬轀醱钜鈒鍚鈃鉶鋩錏銍銛鋙錡錩鑕鍈鍠鎡鎛钂鎔鏰鏐鐶鑞鑱閈闓闍闠闒闤韍頲熲頮頴顒纇颺颭颸颻飀飣飥飿餄餎餏餖餕餜餶饁餺馹駰驫騂駸騌驌騤驦魛魢魨魺鮋鮓鮊鮍鮳鮦鰂鮜鱠鮺鮶鯒鯕鯵鰏鱨鯷鰮鰃鰁鱂鰟鰜鰼鱯鱤鱣鳲鶬鴞鴒鴬鴴鵃鵐鶤鵮鶊鵷鷫鶡鶖鷊鶲鶹鶺鷁鷖鸇鸏鸘黶鼂齕齗捍骯桿茍瑯貍瞇吶剎墑臺兇巖癥準睪蕓芐蘗搟弒脧鉅麼";
    private static byte[][][] s2tMap = new byte[256][256][2];
    private static byte[][][] t2sMap = new byte[256][256][2];

    /**
     * 初始化，调入GBK简体对繁体的码表
     */
    static {
        try {
            { // 初始化繁体转简体表

                byte[] buf1 = GBK_T2S_S_MAP.getBytes(StringPool.Charset.GBK);
                byte[] buf2 = GBK_T2S_T_MAP.getBytes(StringPool.Charset.GBK);

                int len = Math.min(buf1.length, buf2.length);

                for (int i = 0; i < len; i += 2) {
                    t2sMap[buf2[i] & 0xFF][buf2[i + 1] & 0xFF][0] = buf1[i];
                    t2sMap[buf2[i] & 0xFF][buf2[i + 1] & 0xFF][1] = buf1[i + 1];
                }
            }

            { // 初始化简体转繁体表

                byte[] buf1 = GBK_S2T_T_MAP.getBytes(StringPool.Charset.GBK);
                byte[] buf2 = GBK_S2T_S_MAP.getBytes(StringPool.Charset.GBK);

                int len = Math.min(buf1.length, buf2.length);

                for (int i = 0; i < len; i += 2) {
                    s2tMap[buf2[i] & 0xFF][buf2[i + 1] & 0xFF][0] = buf1[i];
                    s2tMap[buf2[i] & 0xFF][buf2[i + 1] & 0xFF][1] = buf1[i + 1];
                }
            }
        } catch (Exception e) {
            logger.error("init GBKMap error", e);
        }
    }

    /**
     * 通过简体码得到繁体码
     * 
     * @param hbyte
     * @param lbyte
     * @return 转换后的byte数组
     */
    public static byte[] getS2TValue(byte hbyte, byte lbyte) {
        return s2tMap[hbyte & 0xFF][lbyte & 0xFF];
    }

    /**
     * 通过繁体码得到简体码
     * 
     * @param hbyte
     * @param lbyte
     * @return 转换后的byte数组
     */
    public static byte[] getT2SValue(byte hbyte, byte lbyte) {
        return t2sMap[hbyte & 0xFF][lbyte & 0xFF];
    }

    /**
     * 从简体转换到繁体
     * 
     * @param str 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String covGBKS2T(String str) {
        return covGBK(str, 0);
    }

    /**
     * 从繁体转换成简体
     * 
     * @param str 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String covGBKT2S(String str) {
        return covGBK(str, 1);
    }

    /**
     * 从繁体转换成简体 或 从简体转换到繁体
     * 
     * @param string 需要转换的字符串
     * @param type 类型，0：从简体转换到繁体，其他：从繁体转换成简体
     * @return 转换后的字符串
     */
    public static String covGBK(String string, int type) {
        if (null == string) {
            return null;
        }

        try {
            byte[][][] m = ((type == 0) ? s2tMap : t2sMap);
            byte[] bs = string.getBytes(StringPool.Charset.GBK);

            int len = bs.length;

            for (int i = 0; i < (len - 1); i++) {
                int b = bs[i] & 0xFF;
                int b1 = bs[i + 1] & 0xFF;

                // GBK范围8140-FEFE
                if ((b >= 0x81) && (b <= 0xFE) && (b1 >= 0x40) && (b1 <= 0xFE) && (b1 != 0x7F)) {
                    byte[] nbs = m[bs[i] & 0xFF][bs[i + 1] & 0xFF];

                    if ((nbs[0] != 0) && (nbs[1] != 0)) {
                        bs[i] = nbs[0];
                        bs[i + 1] = nbs[1];
                    }

                    i++;
                }
            }

            return new String(bs, StringPool.Charset.GBK);
        } catch (Exception e) {
            logger.error("covGBK error", e);

            return string;
        }
    }

    /**
     * 判断是否包含字符串
     * 
     * @param str 传入字符串
     * @return 如果包含，返回true
     */
    public static boolean isInT2SMap(String str) {
        return GBK_T2S_T_MAP.contains(str);
    }

}
