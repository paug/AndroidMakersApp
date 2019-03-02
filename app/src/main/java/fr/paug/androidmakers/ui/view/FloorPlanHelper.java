package fr.paug.androidmakers.ui.view;

import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;



/**
 * Created by Paug on 2 Mar 2019.
 * Copyright © 2019 Paug. All rights reserved.
 * @author Paug
 */
public class FloorPlanHelper {


    // Resizing Behavior
    public enum ResizingBehavior {
        AspectFit, //!< The content is proportionally resized to fit into the target rectangle.
        AspectFill, //!< The content is proportionally resized to completely fill the target rectangle.
        Stretch, //!< The content is stretched to match the entire target rectangle.
        Center, //!< The content is centered in the target rectangle, but it is NOT resized.
    }

    // In Trial version of PaintCode, the code generation is limited to 3 canvases.

    // Canvas Drawings
    // Tab

    private static class CacheForCanvas1 {
        private static Paint paint = new Paint();
        private static RectF originalFrame = new RectF(0f, 0f, 1869f, 2801f);
        private static RectF resizedFrame = new RectF();
        private static RectF path4703Rect = new RectF();
        private static Path path4703Path = new Path();
        private static RectF path4701Rect = new RectF();
        private static Path path4701Path = new Path();
        private static RectF path4699Rect = new RectF();
        private static Path path4699Path = new Path();
        private static RectF path4697Rect = new RectF();
        private static Path path4697Path = new Path();
        private static RectF path4680Rect = new RectF();
        private static Path path4680Path = new Path();
        private static RectF rect1521Rect = new RectF();
        private static Path rect1521Path = new Path();
        private static RectF rect4667Rect = new RectF();
        private static Path rect4667Path = new Path();
        private static RectF rect4665Rect = new RectF();
        private static Path rect4665Path = new Path();
        private static RectF path2176Rect = new RectF();
        private static Path path2176Path = new Path();
        private static RectF path2180Rect = new RectF();
        private static Path path2180Path = new Path();
        private static RectF path828Rect = new RectF();
        private static Path path828Path = new Path();
        private static RectF path830Rect = new RectF();
        private static Path path830Path = new Path();
        private static RectF path832Rect = new RectF();
        private static Path path832Path = new Path();
        private static RectF path834Rect = new RectF();
        private static Path path834Path = new Path();
        private static RectF path836Rect = new RectF();
        private static Path path836Path = new Path();
        private static RectF path838Rect = new RectF();
        private static Path path838Path = new Path();
        private static RectF path840Rect = new RectF();
        private static Path path840Path = new Path();
        private static RectF path842Rect = new RectF();
        private static Path path842Path = new Path();
        private static RectF path844Rect = new RectF();
        private static Path path844Path = new Path();
        private static RectF path846Rect = new RectF();
        private static Path path846Path = new Path();
        private static RectF path876Rect = new RectF();
        private static Path path876Path = new Path();
        private static RectF path8763Rect = new RectF();
        private static Path path8763Path = new Path();
        private static RectF path8766Rect = new RectF();
        private static Path path8766Path = new Path();
        private static RectF path8767Rect = new RectF();
        private static Path path8767Path = new Path();
        private static RectF path8765Rect = new RectF();
        private static Path path8765Path = new Path();
        private static RectF path87670Rect = new RectF();
        private static Path path87670Path = new Path();
        private static RectF path87639Rect = new RectF();
        private static Path path87639Path = new Path();
        private static RectF path87663Rect = new RectF();
        private static Path path87663Path = new Path();
        private static RectF path87676Rect = new RectF();
        private static Path path87676Path = new Path();
        private static RectF path87650Rect = new RectF();
        private static Path path87650Path = new Path();
        private static RectF path87662Rect = new RectF();
        private static Path path87662Path = new Path();
        private static RectF path87636Rect = new RectF();
        private static Path path87636Path = new Path();
        private static RectF path87661Rect = new RectF();
        private static Path path87661Path = new Path();
        private static RectF path87678Rect = new RectF();
        private static Path path87678Path = new Path();
        private static RectF path87657Rect = new RectF();
        private static Path path87657Path = new Path();
        private static RectF path876709Rect = new RectF();
        private static Path path876709Path = new Path();
        private static RectF path876392Rect = new RectF();
        private static Path path876392Path = new Path();
        private static RectF path876630Rect = new RectF();
        private static Path path876630Path = new Path();
        private static RectF path876762Rect = new RectF();
        private static Path path876762Path = new Path();
        private static RectF path876503Rect = new RectF();
        private static Path path876503Path = new Path();
        private static RectF rect1196Rect = new RectF();
        private static Path rect1196Path = new Path();
        private static RectF path87630Rect = new RectF();
        private static Path path87630Path = new Path();
        private static RectF path87637Rect = new RectF();
        private static Path path87637Path = new Path();
        private static RectF path87668Rect = new RectF();
        private static Path path87668Path = new Path();
        private static RectF path876768Rect = new RectF();
        private static Path path876768Path = new Path();
        private static RectF path87658Rect = new RectF();
        private static Path path87658Path = new Path();
        private static RectF path876704Rect = new RectF();
        private static Path path876704Path = new Path();
        private static RectF path876393Rect = new RectF();
        private static Path path876393Path = new Path();
        private static RectF path876631Rect = new RectF();
        private static Path path876631Path = new Path();
        private static RectF path876764Rect = new RectF();
        private static Path path876764Path = new Path();
        private static RectF path876509Rect = new RectF();
        private static Path path876509Path = new Path();
        private static RectF path876622Rect = new RectF();
        private static Path path876622Path = new Path();
        private static RectF path876360Rect = new RectF();
        private static Path path876360Path = new Path();
        private static RectF path876616Rect = new RectF();
        private static Path path876616Path = new Path();
        private static RectF path876788Rect = new RectF();
        private static Path path876788Path = new Path();
        private static RectF path876579Rect = new RectF();
        private static Path path876579Path = new Path();
        private static RectF path8767092Rect = new RectF();
        private static Path path8767092Path = new Path();
        private static RectF path8763926Rect = new RectF();
        private static Path path8763926Path = new Path();
        private static RectF path8766306Rect = new RectF();
        private static Path path8766306Path = new Path();
        private static RectF path8767624Rect = new RectF();
        private static Path path8767624Path = new Path();
        private static RectF path8765039Rect = new RectF();
        private static Path path8765039Path = new Path();
        private static RectF rect11965Rect = new RectF();
        private static Path rect11965Path = new Path();
        private static RectF path8761Rect = new RectF();
        private static Path path8761Path = new Path();
        private static RectF path87631Rect = new RectF();
        private static Path path87631Path = new Path();
        private static RectF path87665Rect = new RectF();
        private static Path path87665Path = new Path();
        private static RectF path87679Rect = new RectF();
        private static Path path87679Path = new Path();
        private static RectF path876577Rect = new RectF();
        private static Path path876577Path = new Path();
        private static RectF path876706Rect = new RectF();
        private static Path path876706Path = new Path();
        private static RectF path876397Rect = new RectF();
        private static Path path876397Path = new Path();
        private static RectF path876633Rect = new RectF();
        private static Path path876633Path = new Path();
        private static RectF path876766Rect = new RectF();
        private static Path path876766Path = new Path();
        private static RectF path876505Rect = new RectF();
        private static Path path876505Path = new Path();
        private static RectF path876626Rect = new RectF();
        private static Path path876626Path = new Path();
        private static RectF path876363Rect = new RectF();
        private static Path path876363Path = new Path();
        private static RectF path876619Rect = new RectF();
        private static Path path876619Path = new Path();
        private static RectF path876784Rect = new RectF();
        private static Path path876784Path = new Path();
        private static RectF path876578Rect = new RectF();
        private static Path path876578Path = new Path();
        private static RectF path8767091Rect = new RectF();
        private static Path path8767091Path = new Path();
        private static RectF path8763922Rect = new RectF();
        private static Path path8763922Path = new Path();
        private static RectF path8766309Rect = new RectF();
        private static Path path8766309Path = new Path();
        private static RectF path8767623Rect = new RectF();
        private static Path path8767623Path = new Path();
        private static RectF path87650390Rect = new RectF();
        private static Path path87650390Path = new Path();
        private static RectF rect1446Rect = new RectF();
        private static Path rect1446Path = new Path();
        private static RectF path1459Rect = new RectF();
        private static Path path1459Path = new Path();
        private static RectF path1461Rect = new RectF();
        private static Path path1461Path = new Path();
        private static RectF path1463Rect = new RectF();
        private static Path path1463Path = new Path();
        private static RectF rect1519Rect = new RectF();
        private static Path rect1519Path = new Path();
        private static RectF path8308Rect = new RectF();
        private static Path path8308Path = new Path();
        private static RectF path8768Rect = new RectF();
        private static Path path8768Path = new Path();
        private static RectF path87635Rect = new RectF();
        private static Path path87635Path = new Path();
        private static RectF path87660Rect = new RectF();
        private static Path path87660Path = new Path();
        private static RectF path876796Rect = new RectF();
        private static Path path876796Path = new Path();
        private static RectF path87653Rect = new RectF();
        private static Path path87653Path = new Path();
        private static RectF path876708Rect = new RectF();
        private static Path path876708Path = new Path();
        private static RectF path876395Rect = new RectF();
        private static Path path876395Path = new Path();
        private static RectF path876636Rect = new RectF();
        private static Path path876636Path = new Path();
        private static RectF path876761Rect = new RectF();
        private static Path path876761Path = new Path();
        private static RectF path876501Rect = new RectF();
        private static Path path876501Path = new Path();
        private static RectF path876625Rect = new RectF();
        private static Path path876625Path = new Path();
        private static RectF path876369Rect = new RectF();
        private static Path path876369Path = new Path();
        private static RectF path876618Rect = new RectF();
        private static Path path876618Path = new Path();
        private static RectF path8767848Rect = new RectF();
        private static Path path8767848Path = new Path();
        private static RectF path876571Rect = new RectF();
        private static Path path876571Path = new Path();
        private static RectF path8767090Rect = new RectF();
        private static Path path8767090Path = new Path();
        private static RectF path8763923Rect = new RectF();
        private static Path path8763923Path = new Path();
        private static RectF path8766300Rect = new RectF();
        private static Path path8766300Path = new Path();
        private static RectF path87676244Rect = new RectF();
        private static Path path87676244Path = new Path();
        private static RectF path8765034Rect = new RectF();
        private static Path path8765034Path = new Path();
        private static RectF rect11964Rect = new RectF();
        private static Path rect11964Path = new Path();
        private static RectF path876307Rect = new RectF();
        private static Path path876307Path = new Path();
        private static RectF path876376Rect = new RectF();
        private static Path path876376Path = new Path();
        private static RectF path876683Rect = new RectF();
        private static Path path876683Path = new Path();
        private static RectF path8767681Rect = new RectF();
        private static Path path8767681Path = new Path();
        private static RectF path876587Rect = new RectF();
        private static Path path876587Path = new Path();
        private static RectF path8767045Rect = new RectF();
        private static Path path8767045Path = new Path();
        private static RectF path8763939Rect = new RectF();
        private static Path path8763939Path = new Path();
        private static RectF path8766316Rect = new RectF();
        private static Path path8766316Path = new Path();
        private static RectF path8767642Rect = new RectF();
        private static Path path8767642Path = new Path();
        private static RectF path8765091Rect = new RectF();
        private static Path path8765091Path = new Path();
        private static RectF path8766227Rect = new RectF();
        private static Path path8766227Path = new Path();
        private static RectF path8763608Rect = new RectF();
        private static Path path8763608Path = new Path();
        private static RectF path8766165Rect = new RectF();
        private static Path path8766165Path = new Path();
        private static RectF path8767887Rect = new RectF();
        private static Path path8767887Path = new Path();
        private static RectF path8765794Rect = new RectF();
        private static Path path8765794Path = new Path();
        private static RectF path87670921Rect = new RectF();
        private static Path path87670921Path = new Path();
        private static RectF path87639268Rect = new RectF();
        private static Path path87639268Path = new Path();
        private static RectF path87663065Rect = new RectF();
        private static Path path87663065Path = new Path();
        private static RectF path87676249Rect = new RectF();
        private static Path path87676249Path = new Path();
        private static RectF path87650397Rect = new RectF();
        private static Path path87650397Path = new Path();
        private static RectF rect119655Rect = new RectF();
        private static Path rect119655Path = new Path();
        private static RectF path87613Rect = new RectF();
        private static Path path87613Path = new Path();
        private static RectF path876318Rect = new RectF();
        private static Path path876318Path = new Path();
        private static RectF path876658Rect = new RectF();
        private static Path path876658Path = new Path();
        private static RectF path876793Rect = new RectF();
        private static Path path876793Path = new Path();
        private static RectF path8765771Rect = new RectF();
        private static Path path8765771Path = new Path();
        private static RectF path8767068Rect = new RectF();
        private static Path path8767068Path = new Path();
        private static RectF path8763979Rect = new RectF();
        private static Path path8763979Path = new Path();
        private static RectF path8766336Rect = new RectF();
        private static Path path8766336Path = new Path();
        private static RectF path8767664Rect = new RectF();
        private static Path path8767664Path = new Path();
        private static RectF path8765053Rect = new RectF();
        private static Path path8765053Path = new Path();
        private static RectF path8766263Rect = new RectF();
        private static Path path8766263Path = new Path();
        private static RectF path8763633Rect = new RectF();
        private static Path path8763633Path = new Path();
        private static RectF path8766198Rect = new RectF();
        private static Path path8766198Path = new Path();
        private static RectF path8767846Rect = new RectF();
        private static Path path8767846Path = new Path();
        private static RectF path8765780Rect = new RectF();
        private static Path path8765780Path = new Path();
        private static RectF path87670914Rect = new RectF();
        private static Path path87670914Path = new Path();
        private static RectF path87639228Rect = new RectF();
        private static Path path87639228Path = new Path();
        private static RectF path87663098Rect = new RectF();
        private static Path path87663098Path = new Path();
        private static RectF path87676238Rect = new RectF();
        private static Path path87676238Path = new Path();
        private static RectF path876503909Rect = new RectF();
        private static Path path876503909Path = new Path();
        private static RectF path8287Rect = new RectF();
        private static Path path8287Path = new Path();
        private static RectF path8307Rect = new RectF();
        private static Path path8307Path = new Path();
        private static RectF path8449Rect = new RectF();
        private static Path path8449Path = new Path();
        private static RectF path8462Rect = new RectF();
        private static Path path8462Path = new Path();
        private static RectF path87654Rect = new RectF();
        private static Path path87654Path = new Path();
        private static RectF path87632Rect = new RectF();
        private static Path path87632Path = new Path();
        private static RectF path876659Rect = new RectF();
        private static Path path876659Path = new Path();
        private static RectF path87674Rect = new RectF();
        private static Path path87674Path = new Path();
        private static RectF path87656Rect = new RectF();
        private static Path path87656Path = new Path();
        private static RectF path876702Rect = new RectF();
        private static Path path876702Path = new Path();
        private static RectF path8763924Rect = new RectF();
        private static Path path8763924Path = new Path();
        private static RectF path876637Rect = new RectF();
        private static Path path876637Path = new Path();
        private static RectF path876767Rect = new RectF();
        private static Path path876767Path = new Path();
        private static RectF path8765054Rect = new RectF();
        private static Path path8765054Path = new Path();
        private static RectF path876628Rect = new RectF();
        private static Path path876628Path = new Path();
        private static RectF path876361Rect = new RectF();
        private static Path path876361Path = new Path();
        private static RectF path876612Rect = new RectF();
        private static Path path876612Path = new Path();
        private static RectF path8767889Rect = new RectF();
        private static Path path8767889Path = new Path();
        private static RectF path876573Rect = new RectF();
        private static Path path876573Path = new Path();
        private static RectF path8767096Rect = new RectF();
        private static Path path8767096Path = new Path();
        private static RectF path8763928Rect = new RectF();
        private static Path path8763928Path = new Path();
        private static RectF path87663002Rect = new RectF();
        private static Path path87663002Path = new Path();
        private static RectF path8767621Rect = new RectF();
        private static Path path8767621Path = new Path();
        private static RectF path8765030Rect = new RectF();
        private static Path path8765030Path = new Path();
        private static RectF rect119651Rect = new RectF();
        private static Path rect119651Path = new Path();
        private static RectF path876301Rect = new RectF();
        private static Path path876301Path = new Path();
        private static RectF path876370Rect = new RectF();
        private static Path path876370Path = new Path();
        private static RectF path876688Rect = new RectF();
        private static Path path876688Path = new Path();
        private static RectF path8767685Rect = new RectF();
        private static Path path8767685Path = new Path();
        private static RectF path876580Rect = new RectF();
        private static Path path876580Path = new Path();
        private static RectF path8767046Rect = new RectF();
        private static Path path8767046Path = new Path();
        private static RectF path8763934Rect = new RectF();
        private static Path path8763934Path = new Path();
        private static RectF path87663162Rect = new RectF();
        private static Path path87663162Path = new Path();
        private static RectF path8767645Rect = new RectF();
        private static Path path8767645Path = new Path();
        private static RectF path8765098Rect = new RectF();
        private static Path path8765098Path = new Path();
        private static RectF path8766226Rect = new RectF();
        private static Path path8766226Path = new Path();
        private static RectF path8763602Rect = new RectF();
        private static Path path8763602Path = new Path();
        private static RectF path8766168Rect = new RectF();
        private static Path path8766168Path = new Path();
        private static RectF path8767884Rect = new RectF();
        private static Path path8767884Path = new Path();
        private static RectF path8765797Rect = new RectF();
        private static Path path8765797Path = new Path();
        private static RectF path87670922Rect = new RectF();
        private static Path path87670922Path = new Path();
        private static RectF path87639264Rect = new RectF();
        private static Path path87639264Path = new Path();
        private static RectF path87663060Rect = new RectF();
        private static Path path87663060Path = new Path();
        private static RectF path87676246Rect = new RectF();
        private static Path path87676246Path = new Path();
        private static RectF path87650392Rect = new RectF();
        private static Path path87650392Path = new Path();
        private static RectF rect119659Rect = new RectF();
        private static Path rect119659Path = new Path();
        private static RectF path87619Rect = new RectF();
        private static Path path87619Path = new Path();
        private static RectF path876310Rect = new RectF();
        private static Path path876310Path = new Path();
        private static RectF path8766581Rect = new RectF();
        private static Path path8766581Path = new Path();
        private static RectF path8767931Rect = new RectF();
        private static Path path8767931Path = new Path();
        private static RectF path87657710Rect = new RectF();
        private static Path path87657710Path = new Path();
        private static RectF path8767063Rect = new RectF();
        private static Path path8767063Path = new Path();
        private static RectF path8763974Rect = new RectF();
        private static Path path8763974Path = new Path();
        private static RectF path8766330Rect = new RectF();
        private static Path path8766330Path = new Path();
        private static RectF path8767663Rect = new RectF();
        private static Path path8767663Path = new Path();
        private static RectF path8765059Rect = new RectF();
        private static Path path8765059Path = new Path();
        private static RectF path8766261Rect = new RectF();
        private static Path path8766261Path = new Path();
        private static RectF path8763639Rect = new RectF();
        private static Path path8763639Path = new Path();
        private static RectF path8766196Rect = new RectF();
        private static Path path8766196Path = new Path();
        private static RectF path8767849Rect = new RectF();
        private static Path path8767849Path = new Path();
        private static RectF path8765783Rect = new RectF();
        private static Path path8765783Path = new Path();
        private static RectF path87670913Rect = new RectF();
        private static Path path87670913Path = new Path();
        private static RectF path876392280Rect = new RectF();
        private static Path path876392280Path = new Path();
        private static RectF path87663095Rect = new RectF();
        private static Path path87663095Path = new Path();
        private static RectF path87676236Rect = new RectF();
        private static Path path87676236Path = new Path();
        private static RectF path876503906Rect = new RectF();
        private static Path path876503906Path = new Path();
        private static RectF rect14464Rect = new RectF();
        private static Path rect14464Path = new Path();
        private static RectF rect2182Rect = new RectF();
        private static Path rect2182Path = new Path();
        private static RectF rect2178Rect = new RectF();
        private static Path rect2178Path = new Path();
        private static RectF path8286Rect = new RectF();
        private static Path path8286Path = new Path();
        private static RectF path8302Rect = new RectF();
        private static Path path8302Path = new Path();
        private static RectF path8423Rect = new RectF();
        private static Path path8423Path = new Path();
        private static RectF path8446Rect = new RectF();
        private static Path path8446Path = new Path();
        private static RectF path8460Rect = new RectF();
        private static Path path8460Path = new Path();
        private static RectF path8762Rect = new RectF();
        private static Path path8762Path = new Path();
        private static RectF path87633Rect = new RectF();
        private static Path path87633Path = new Path();
        private static RectF path87669Rect = new RectF();
        private static Path path87669Path = new Path();
        private static RectF path876743Rect = new RectF();
        private static Path path876743Path = new Path();
        private static RectF path87655Rect = new RectF();
        private static Path path87655Path = new Path();
        private static RectF path876701Rect = new RectF();
        private static Path path876701Path = new Path();
        private static RectF path876394Rect = new RectF();
        private static Path path876394Path = new Path();
        private static RectF path8766331Rect = new RectF();
        private static Path path8766331Path = new Path();
        private static RectF path8767646Rect = new RectF();
        private static Path path8767646Path = new Path();
        private static RectF path8765094Rect = new RectF();
        private static Path path8765094Path = new Path();
        private static RectF path8766222Rect = new RectF();
        private static Path path8766222Path = new Path();
        private static RectF path876366Rect = new RectF();
        private static Path path876366Path = new Path();
        private static RectF path876614Rect = new RectF();
        private static Path path876614Path = new Path();
        private static RectF path876781Rect = new RectF();
        private static Path path876781Path = new Path();
        private static RectF path876572Rect = new RectF();
        private static Path path876572Path = new Path();
        private static RectF path8767098Rect = new RectF();
        private static Path path8767098Path = new Path();
        private static RectF path87639289Rect = new RectF();
        private static Path path87639289Path = new Path();
        private static RectF path8766302Rect = new RectF();
        private static Path path8766302Path = new Path();
        private static RectF path8767628Rect = new RectF();
        private static Path path8767628Path = new Path();
        private static RectF path8765038Rect = new RectF();
        private static Path path8765038Path = new Path();
        private static RectF rect11968Rect = new RectF();
        private static Path rect11968Path = new Path();
        private static RectF path876306Rect = new RectF();
        private static Path path876306Path = new Path();
        private static RectF path876378Rect = new RectF();
        private static Path path876378Path = new Path();
        private static RectF path8766838Rect = new RectF();
        private static Path path8766838Path = new Path();
        private static RectF path8767683Rect = new RectF();
        private static Path path8767683Path = new Path();
        private static RectF path876583Rect = new RectF();
        private static Path path876583Path = new Path();
        private static RectF path8767043Rect = new RectF();
        private static Path path8767043Path = new Path();
        private static RectF path8763938Rect = new RectF();
        private static Path path8763938Path = new Path();
        private static RectF path8766310Rect = new RectF();
        private static Path path8766310Path = new Path();
        private static RectF path8767644Rect = new RectF();
        private static Path path8767644Path = new Path();
        private static RectF path8765097Rect = new RectF();
        private static Path path8765097Path = new Path();
        private static RectF path87662268Rect = new RectF();
        private static Path path87662268Path = new Path();
        private static RectF path8763609Rect = new RectF();
        private static Path path8763609Path = new Path();
        private static RectF path8766160Rect = new RectF();
        private static Path path8766160Path = new Path();
        private static RectF path8767886Rect = new RectF();
        private static Path path8767886Path = new Path();
        private static RectF path8765798Rect = new RectF();
        private static Path path8765798Path = new Path();
        private static RectF path87670927Rect = new RectF();
        private static Path path87670927Path = new Path();
        private static RectF path87639269Rect = new RectF();
        private static Path path87639269Path = new Path();
        private static RectF path876630603Rect = new RectF();
        private static Path path876630603Path = new Path();
        private static RectF path87676243Rect = new RectF();
        private static Path path87676243Path = new Path();
        private static RectF path87650393Rect = new RectF();
        private static Path path87650393Path = new Path();
        private static RectF rect119657Rect = new RectF();
        private static Path rect119657Path = new Path();
        private static RectF path876132Rect = new RectF();
        private static Path path876132Path = new Path();
        private static RectF path876316Rect = new RectF();
        private static Path path876316Path = new Path();
        private static RectF path876655Rect = new RectF();
        private static Path path876655Path = new Path();
        private static RectF path876792Rect = new RectF();
        private static Path path876792Path = new Path();
        private static RectF path8765776Rect = new RectF();
        private static Path path8765776Path = new Path();
        private static RectF path8767065Rect = new RectF();
        private static Path path8767065Path = new Path();
        private static RectF path8763978Rect = new RectF();
        private static Path path8763978Path = new Path();
        private static RectF path8766337Rect = new RectF();
        private static Path path8766337Path = new Path();
        private static RectF path8767669Rect = new RectF();
        private static Path path8767669Path = new Path();
        private static RectF path8765056Rect = new RectF();
        private static Path path8765056Path = new Path();
        private static RectF path8766260Rect = new RectF();
        private static Path path8766260Path = new Path();
        private static RectF path8763634Rect = new RectF();
        private static Path path8763634Path = new Path();
        private static RectF path8766191Rect = new RectF();
        private static Path path8766191Path = new Path();
        private static RectF path8767840Rect = new RectF();
        private static Path path8767840Path = new Path();
        private static RectF path8765784Rect = new RectF();
        private static Path path8765784Path = new Path();
        private static RectF path87670918Rect = new RectF();
        private static Path path87670918Path = new Path();
        private static RectF path87639227Rect = new RectF();
        private static Path path87639227Path = new Path();
        private static RectF path87663090Rect = new RectF();
        private static Path path87663090Path = new Path();
        private static RectF path876762386Rect = new RectF();
        private static Path path876762386Path = new Path();
        private static RectF path876503902Rect = new RectF();
        private static Path path876503902Path = new Path();
        private static RectF rect144647Rect = new RectF();
        private static Path rect144647Path = new Path();
        private static RectF rect15218Rect = new RectF();
        private static Path rect15218Path = new Path();
        private static RectF path83083Rect = new RectF();
        private static Path path83083Path = new Path();
        private static RectF path87680Rect = new RectF();
        private static Path path87680Path = new Path();
        private static RectF path876351Rect = new RectF();
        private static Path path876351Path = new Path();
        private static RectF path876607Rect = new RectF();
        private static Path path876607Path = new Path();
        private static RectF path8767968Rect = new RectF();
        private static Path path8767968Path = new Path();
        private static RectF path876539Rect = new RectF();
        private static Path path876539Path = new Path();
        private static RectF path8767081Rect = new RectF();
        private static Path path8767081Path = new Path();
        private static RectF path8763955Rect = new RectF();
        private static Path path8763955Path = new Path();
        private static RectF path8766364Rect = new RectF();
        private static Path path8766364Path = new Path();
        private static RectF path8767619Rect = new RectF();
        private static Path path8767619Path = new Path();
        private static RectF path8765012Rect = new RectF();
        private static Path path8765012Path = new Path();
        private static RectF path8766255Rect = new RectF();
        private static Path path8766255Path = new Path();
        private static RectF path8763697Rect = new RectF();
        private static Path path8763697Path = new Path();
        private static RectF path8766184Rect = new RectF();
        private static Path path8766184Path = new Path();
        private static RectF path87678489Rect = new RectF();
        private static Path path87678489Path = new Path();
        private static RectF path8765719Rect = new RectF();
        private static Path path8765719Path = new Path();
        private static RectF path87670904Rect = new RectF();
        private static Path path87670904Path = new Path();
        private static RectF path87639235Rect = new RectF();
        private static Path path87639235Path = new Path();
        private static RectF path87663009Rect = new RectF();
        private static Path path87663009Path = new Path();
        private static RectF path876762443Rect = new RectF();
        private static Path path876762443Path = new Path();
        private static RectF path87650345Rect = new RectF();
        private static Path path87650345Path = new Path();
        private static RectF rect119647Rect = new RectF();
        private static Path rect119647Path = new Path();
        private static RectF path8763070Rect = new RectF();
        private static Path path8763070Path = new Path();
        private static RectF path8763768Rect = new RectF();
        private static Path path8763768Path = new Path();
        private static RectF path8766831Rect = new RectF();
        private static Path path8766831Path = new Path();
        private static RectF path87676819Rect = new RectF();
        private static Path path87676819Path = new Path();
        private static RectF path8765879Rect = new RectF();
        private static Path path8765879Path = new Path();
        private static RectF path87670457Rect = new RectF();
        private static Path path87670457Path = new Path();
        private static RectF path87639398Rect = new RectF();
        private static Path path87639398Path = new Path();
        private static RectF path87663163Rect = new RectF();
        private static Path path87663163Path = new Path();
        private static RectF path87676425Rect = new RectF();
        private static Path path87676425Path = new Path();
        private static RectF path87650913Rect = new RectF();
        private static Path path87650913Path = new Path();
        private static RectF path87662274Rect = new RectF();
        private static Path path87662274Path = new Path();
        private static RectF path87636089Rect = new RectF();
        private static Path path87636089Path = new Path();
        private static RectF path87661650Rect = new RectF();
        private static Path path87661650Path = new Path();
        private static RectF path87678872Rect = new RectF();
        private static Path path87678872Path = new Path();
        private static RectF path87657940Rect = new RectF();
        private static Path path87657940Path = new Path();
        private static RectF path876709211Rect = new RectF();
        private static Path path876709211Path = new Path();
        private static RectF path876392689Rect = new RectF();
        private static Path path876392689Path = new Path();
        private static RectF path876630656Rect = new RectF();
        private static Path path876630656Path = new Path();
        private static RectF path876762492Rect = new RectF();
        private static Path path876762492Path = new Path();
        private static RectF path876503971Rect = new RectF();
        private static Path path876503971Path = new Path();
        private static RectF rect1196552Rect = new RectF();
        private static Path rect1196552Path = new Path();
        private static RectF path876130Rect = new RectF();
        private static Path path876130Path = new Path();
        private static RectF path8763187Rect = new RectF();
        private static Path path8763187Path = new Path();
        private static RectF path8766583Rect = new RectF();
        private static Path path8766583Path = new Path();
        private static RectF path8767932Rect = new RectF();
        private static Path path8767932Path = new Path();
        private static RectF path87657711Rect = new RectF();
        private static Path path87657711Path = new Path();
        private static RectF path87670689Rect = new RectF();
        private static Path path87670689Path = new Path();
        private static RectF path87639790Rect = new RectF();
        private static Path path87639790Path = new Path();
        private static RectF path87663365Rect = new RectF();
        private static Path path87663365Path = new Path();
        private static RectF path87676646Rect = new RectF();
        private static Path path87676646Path = new Path();
        private static RectF path87650537Rect = new RectF();
        private static Path path87650537Path = new Path();
        private static RectF path87662637Rect = new RectF();
        private static Path path87662637Path = new Path();
        private static RectF path87636334Rect = new RectF();
        private static Path path87636334Path = new Path();
        private static RectF path87661980Rect = new RectF();
        private static Path path87661980Path = new Path();
        private static RectF path87678466Rect = new RectF();
        private static Path path87678466Path = new Path();
        private static RectF path87657804Rect = new RectF();
        private static Path path87657804Path = new Path();
        private static RectF path876709147Rect = new RectF();
        private static Path path876709147Path = new Path();
        private static RectF path876392284Rect = new RectF();
        private static Path path876392284Path = new Path();
        private static RectF path876630988Rect = new RectF();
        private static Path path876630988Path = new Path();
        private static RectF path876762385Rect = new RectF();
        private static Path path876762385Path = new Path();
        private static RectF path8765039098Rect = new RectF();
        private static Path path8765039098Path = new Path();
        private static RectF rect152182Rect = new RectF();
        private static Path rect152182Path = new Path();
        private static RectF path82860Rect = new RectF();
        private static Path path82860Path = new Path();
        private static RectF path8306Rect = new RectF();
        private static Path path8306Path = new Path();
        private static RectF path8366Rect = new RectF();
        private static Path path8366Path = new Path();
        private static RectF path8429Rect = new RectF();
        private static Path path8429Path = new Path();
        private static RectF path84460Rect = new RectF();
        private static Path path84460Path = new Path();
        private static RectF path8467Rect = new RectF();
        private static Path path8467Path = new Path();
        private static RectF path8760Rect = new RectF();
        private static Path path8760Path = new Path();
        private static RectF path87634Rect = new RectF();
        private static Path path87634Path = new Path();
        private static RectF path876613Rect = new RectF();
        private static Path path876613Path = new Path();
        private static RectF path87677Rect = new RectF();
        private static Path path87677Path = new Path();
        private static RectF path87652Rect = new RectF();
        private static Path path87652Path = new Path();
        private static RectF path8767064Rect = new RectF();
        private static Path path8767064Path = new Path();
        private static RectF path8763952Rect = new RectF();
        private static Path path8763952Path = new Path();
        private static RectF path876632Rect = new RectF();
        private static Path path876632Path = new Path();
        private static RectF path876769Rect = new RectF();
        private static Path path876769Path = new Path();
        private static RectF path876500Rect = new RectF();
        private static Path path876500Path = new Path();
        private static RectF path876629Rect = new RectF();
        private static Path path876629Path = new Path();
        private static RectF path8763694Rect = new RectF();
        private static Path path8763694Path = new Path();
        private static RectF path876615Rect = new RectF();
        private static Path path876615Path = new Path();
        private static RectF path8767810Rect = new RectF();
        private static Path path8767810Path = new Path();
        private static RectF path8765737Rect = new RectF();
        private static Path path8765737Path = new Path();
        private static RectF path87670988Rect = new RectF();
        private static Path path87670988Path = new Path();
        private static RectF path87639260Rect = new RectF();
        private static Path path87639260Path = new Path();
        private static RectF path8766304Rect = new RectF();
        private static Path path8766304Path = new Path();
        private static RectF path8767626Rect = new RectF();
        private static Path path8767626Path = new Path();
        private static RectF path8765037Rect = new RectF();
        private static Path path8765037Path = new Path();
        private static RectF rect11966Rect = new RectF();
        private static Path rect11966Path = new Path();
        private static RectF path876300Rect = new RectF();
        private static Path path876300Path = new Path();
        private static RectF path876379Rect = new RectF();
        private static Path path876379Path = new Path();
        private static RectF path876687Rect = new RectF();
        private static Path path876687Path = new Path();
        private static RectF path87676859Rect = new RectF();
        private static Path path87676859Path = new Path();
        private static RectF path8765878Rect = new RectF();
        private static Path path8765878Path = new Path();
        private static RectF path87670453Rect = new RectF();
        private static Path path87670453Path = new Path();
        private static RectF path8763933Rect = new RectF();
        private static Path path8763933Path = new Path();
        private static RectF path8766318Rect = new RectF();
        private static Path path8766318Path = new Path();
        private static RectF path8767643Rect = new RectF();
        private static Path path8767643Path = new Path();
        private static RectF path87650979Rect = new RectF();
        private static Path path87650979Path = new Path();
        private static RectF path8766223Rect = new RectF();
        private static Path path8766223Path = new Path();
        private static RectF path8763607Rect = new RectF();
        private static Path path8763607Path = new Path();
        private static RectF path87661687Rect = new RectF();
        private static Path path87661687Path = new Path();
        private static RectF path87678841Rect = new RectF();
        private static Path path87678841Path = new Path();
        private static RectF path8765799Rect = new RectF();
        private static Path path8765799Path = new Path();
        private static RectF path87670920Rect = new RectF();
        private static Path path87670920Path = new Path();
        private static RectF path876392698Rect = new RectF();
        private static Path path876392698Path = new Path();
        private static RectF path87663068Rect = new RectF();
        private static Path path87663068Path = new Path();
        private static RectF path87676245Rect = new RectF();
        private static Path path87676245Path = new Path();
        private static RectF path87650398Rect = new RectF();
        private static Path path87650398Path = new Path();
        private static RectF rect119654Rect = new RectF();
        private static Path rect119654Path = new Path();
        private static RectF path876137Rect = new RectF();
        private static Path path876137Path = new Path();
        private static RectF path876311Rect = new RectF();
        private static Path path876311Path = new Path();
        private static RectF path876653Rect = new RectF();
        private static Path path876653Path = new Path();
        private static RectF path876798Rect = new RectF();
        private static Path path876798Path = new Path();
        private static RectF path8765770Rect = new RectF();
        private static Path path8765770Path = new Path();
        private static RectF path8767069Rect = new RectF();
        private static Path path8767069Path = new Path();
        private static RectF path8763977Rect = new RectF();
        private static Path path8763977Path = new Path();
        private static RectF path8766339Rect = new RectF();
        private static Path path8766339Path = new Path();
        private static RectF path87676693Rect = new RectF();
        private static Path path87676693Path = new Path();
        private static RectF path8765052Rect = new RectF();
        private static Path path8765052Path = new Path();
        private static RectF path8766264Rect = new RectF();
        private static Path path8766264Path = new Path();
        private static RectF path87636337Rect = new RectF();
        private static Path path87636337Path = new Path();
        private static RectF path87661912Rect = new RectF();
        private static Path path87661912Path = new Path();
        private static RectF path8767842Rect = new RectF();
        private static Path path8767842Path = new Path();
        private static RectF path87657802Rect = new RectF();
        private static Path path87657802Path = new Path();
        private static RectF path87670911Rect = new RectF();
        private static Path path87670911Path = new Path();
        private static RectF path876392275Rect = new RectF();
        private static Path path876392275Path = new Path();
        private static RectF path87663091Rect = new RectF();
        private static Path path87663091Path = new Path();
        private static RectF path87676237Rect = new RectF();
        private static Path path87676237Path = new Path();
        private static RectF path876503904Rect = new RectF();
        private static Path path876503904Path = new Path();
        private static RectF rect14461Rect = new RectF();
        private static Path rect14461Path = new Path();
        private static RectF path14597Rect = new RectF();
        private static Path path14597Path = new Path();
        private static RectF path14611Rect = new RectF();
        private static Path path14611Path = new Path();
        private static RectF rect15191Rect = new RectF();
        private static Path rect15191Path = new Path();
        private static RectF path83080Rect = new RectF();
        private static Path path83080Path = new Path();
        private static RectF path87684Rect = new RectF();
        private static Path path87684Path = new Path();
        private static RectF path876350Rect = new RectF();
        private static Path path876350Path = new Path();
        private static RectF path876608Rect = new RectF();
        private static Path path876608Path = new Path();
        private static RectF path8767965Rect = new RectF();
        private static Path path8767965Path = new Path();
        private static RectF path876531Rect = new RectF();
        private static Path path876531Path = new Path();
        private static RectF path8767086Rect = new RectF();
        private static Path path8767086Path = new Path();
        private static RectF path8763956Rect = new RectF();
        private static Path path8763956Path = new Path();
        private static RectF path8766362Rect = new RectF();
        private static Path path8766362Path = new Path();
        private static RectF path8767611Rect = new RectF();
        private static Path path8767611Path = new Path();
        private static RectF path8765019Rect = new RectF();
        private static Path path8765019Path = new Path();
        private static RectF path8766256Rect = new RectF();
        private static Path path8766256Path = new Path();
        private static RectF path8763692Rect = new RectF();
        private static Path path8763692Path = new Path();
        private static RectF path8766188Rect = new RectF();
        private static Path path8766188Path = new Path();
        private static RectF path87678480Rect = new RectF();
        private static Path path87678480Path = new Path();
        private static RectF path8765718Rect = new RectF();
        private static Path path8765718Path = new Path();
        private static RectF path87670901Rect = new RectF();
        private static Path path87670901Path = new Path();
        private static RectF path87639230Rect = new RectF();
        private static Path path87639230Path = new Path();
        private static RectF path87663003Rect = new RectF();
        private static Path path87663003Path = new Path();
        private static RectF path876762442Rect = new RectF();
        private static Path path876762442Path = new Path();
        private static RectF path87650349Rect = new RectF();
        private static Path path87650349Path = new Path();
        private static RectF rect1196475Rect = new RectF();
        private static Path rect1196475Path = new Path();
        private static RectF path8763076Rect = new RectF();
        private static Path path8763076Path = new Path();
        private static RectF path8763764Rect = new RectF();
        private static Path path8763764Path = new Path();
        private static RectF path8766836Rect = new RectF();
        private static Path path8766836Path = new Path();
        private static RectF path87676813Rect = new RectF();
        private static Path path87676813Path = new Path();
        private static RectF path8765877Rect = new RectF();
        private static Path path8765877Path = new Path();
        private static RectF path87670459Rect = new RectF();
        private static Path path87670459Path = new Path();
        private static RectF path87639397Rect = new RectF();
        private static Path path87639397Path = new Path();
        private static RectF path87663164Rect = new RectF();
        private static Path path87663164Path = new Path();
        private static RectF path87676429Rect = new RectF();
        private static Path path87676429Path = new Path();
        private static RectF path87650911Rect = new RectF();
        private static Path path87650911Path = new Path();
        private static RectF path87662277Rect = new RectF();
        private static Path path87662277Path = new Path();
        private static RectF path87636080Rect = new RectF();
        private static Path path87636080Path = new Path();
        private static RectF path87661656Rect = new RectF();
        private static Path path87661656Path = new Path();
        private static RectF path87678870Rect = new RectF();
        private static Path path87678870Path = new Path();
        private static RectF path87657948Rect = new RectF();
        private static Path path87657948Path = new Path();
        private static RectF path876709215Rect = new RectF();
        private static Path path876709215Path = new Path();
        private static RectF path876392683Rect = new RectF();
        private static Path path876392683Path = new Path();
        private static RectF path876630659Rect = new RectF();
        private static Path path876630659Path = new Path();
        private static RectF path876762494Rect = new RectF();
        private static Path path876762494Path = new Path();
        private static RectF path8765039715Rect = new RectF();
        private static Path path8765039715Path = new Path();
        private static RectF rect1196554Rect = new RectF();
        private static Path rect1196554Path = new Path();
        private static RectF path876131Rect = new RectF();
        private static Path path876131Path = new Path();
        private static RectF path8763185Rect = new RectF();
        private static Path path8763185Path = new Path();
        private static RectF path8766585Rect = new RectF();
        private static Path path8766585Path = new Path();
        private static RectF path8767934Rect = new RectF();
        private static Path path8767934Path = new Path();
        private static RectF path87657719Rect = new RectF();
        private static Path path87657719Path = new Path();
        private static RectF path87670688Rect = new RectF();
        private static Path path87670688Path = new Path();
        private static RectF path87639793Rect = new RectF();
        private static Path path87639793Path = new Path();
        private static RectF path87663368Rect = new RectF();
        private static Path path87663368Path = new Path();
        private static RectF path87676645Rect = new RectF();
        private static Path path87676645Path = new Path();
        private static RectF path87650532Rect = new RectF();
        private static Path path87650532Path = new Path();
        private static RectF path87662632Rect = new RectF();
        private static Path path87662632Path = new Path();
        private static RectF path87636332Rect = new RectF();
        private static Path path87636332Path = new Path();
        private static RectF path87661987Rect = new RectF();
        private static Path path87661987Path = new Path();
        private static RectF path87678460Rect = new RectF();
        private static Path path87678460Path = new Path();
        private static RectF path87657803Rect = new RectF();
        private static Path path87657803Path = new Path();
        private static RectF path876709144Rect = new RectF();
        private static Path path876709144Path = new Path();
        private static RectF path876392286Rect = new RectF();
        private static Path path876392286Path = new Path();
        private static RectF path876630983Rect = new RectF();
        private static Path path876630983Path = new Path();
        private static RectF path876762382Rect = new RectF();
        private static Path path876762382Path = new Path();
        private static RectF path8765039093Rect = new RectF();
        private static Path path8765039093Path = new Path();
        private static RectF rect470761Rect = new RectF();
        private static Path rect470761Path = new Path();
        private static RectF rect470766Rect = new RectF();
        private static Path rect470766Path = new Path();
        private static RectF path1465Rect = new RectF();
        private static RectF path1467Rect = new RectF();
        private static Path path1467Path = new Path();
        private static RectF path14654Rect = new RectF();
        private static RectF path14674Rect = new RectF();
        private static Path path14674Path = new Path();
        private static RectF path14657Rect = new RectF();
        private static RectF path14672Rect = new RectF();
        private static Path path14672Path = new Path();
        private static RectF path5127Rect = new RectF();
        private static Path path5127Path = new Path();
        private static RectF path5129Rect = new RectF();
        private static Path path5129Path = new Path();
        private static RectF path5131Rect = new RectF();
        private static Path path5131Path = new Path();
        private static RectF path5133Rect = new RectF();
        private static Path path5133Path = new Path();
        private static RectF path5135Rect = new RectF();
        private static Path path5135Path = new Path();
        private static RectF path5137Rect = new RectF();
        private static Path path5137Path = new Path();
        private static RectF path5139Rect = new RectF();
        private static Path path5139Path = new Path();
        private static RectF path5118Rect = new RectF();
        private static Path path5118Path = new Path();
        private static RectF path5120Rect = new RectF();
        private static Path path5120Path = new Path();
        private static RectF path5122Rect = new RectF();
        private static Path path5122Path = new Path();
        private static RectF path5124Rect = new RectF();
        private static Path path5124Path = new Path();
        private static RectF path5148Rect = new RectF();
        private static Path path5148Path = new Path();
        private static RectF path5150Rect = new RectF();
        private static Path path5150Path = new Path();
        private static RectF path5152Rect = new RectF();
        private static Path path5152Path = new Path();
        private static RectF path5155Rect = new RectF();
        private static Path path5155Path = new Path();
        private static RectF path5157Rect = new RectF();
        private static Path path5157Path = new Path();
        private static RectF path5159Rect = new RectF();
        private static Path path5159Path = new Path();
        private static RectF path3349Rect = new RectF();
        private static Path path3349Path = new Path();
        private static RectF path3351Rect = new RectF();
        private static RectF path33493Rect = new RectF();
        private static Path path33493Path = new Path();
        private static RectF path33518Rect = new RectF();
        private static RectF path3397Rect = new RectF();
        private static Path path3397Path = new Path();
        private static RectF path3399Rect = new RectF();
        private static RectF path3413Rect = new RectF();
        private static RectF path3415Rect = new RectF();
        private static Path path3415Path = new Path();
        private static RectF path3429Rect = new RectF();
        private static RectF path3431Rect = new RectF();
        private static Path path3431Path = new Path();
        private static RectF path4657Rect = new RectF();
        private static Path path4657Path = new Path();
        private static RectF path46573Rect = new RectF();
        private static Path path46573Path = new Path();
        private static RectF path46576Rect = new RectF();
        private static Path path46576Path = new Path();
        private static RectF rect4707Rect = new RectF();
        private static Path rect4707Path = new Path();
        private static RectF path5076Rect = new RectF();
        private static Path path5076Path = new Path();
        private static RectF path5078Rect = new RectF();
        private static Path path5078Path = new Path();
        private static RectF path5080Rect = new RectF();
        private static Path path5080Path = new Path();
        private static RectF path5082Rect = new RectF();
        private static Path path5082Path = new Path();
        private static RectF path5084Rect = new RectF();
        private static Path path5084Path = new Path();
        private static RectF rect47076Rect = new RectF();
        private static Path rect47076Path = new Path();
        private static RectF path5059Rect = new RectF();
        private static Path path5059Path = new Path();
        private static RectF path5061Rect = new RectF();
        private static Path path5061Path = new Path();
        private static RectF path5063Rect = new RectF();
        private static Path path5063Path = new Path();
        private static RectF path5065Rect = new RectF();
        private static Path path5065Path = new Path();
        private static RectF path5067Rect = new RectF();
        private static Path path5067Path = new Path();
        private static RectF path5069Rect = new RectF();
        private static Path path5069Path = new Path();
        private static RectF path5071Rect = new RectF();
        private static Path path5071Path = new Path();
        private static RectF path5073Rect = new RectF();
        private static Path path5073Path = new Path();
        private static RectF path4788Rect = new RectF();
        private static RectF path4790Rect = new RectF();
        private static Path path4790Path = new Path();
        private static RectF path47880Rect = new RectF();
        private static RectF path47906Rect = new RectF();
        private static Path path47906Path = new Path();
        private static RectF path34136Rect = new RectF();
        private static RectF path34151Rect = new RectF();
        private static Path path34151Path = new Path();
        private static RectF path5034Rect = new RectF();
        private static Path path5034Path = new Path();
        private static RectF path5036Rect = new RectF();
        private static Path path5036Path = new Path();
        private static RectF path5038Rect = new RectF();
        private static Path path5038Path = new Path();
        private static RectF path5040Rect = new RectF();
        private static Path path5040Path = new Path();
        private static RectF path5042Rect = new RectF();
        private static Path path5042Path = new Path();
        private static RectF path5044Rect = new RectF();
        private static Path path5044Path = new Path();
        private static RectF path5046Rect = new RectF();
        private static Path path5046Path = new Path();
        private static RectF path5048Rect = new RectF();
        private static Path path5048Path = new Path();
        private static RectF path5050Rect = new RectF();
        private static Path path5050Path = new Path();
        private static RectF path5052Rect = new RectF();
        private static Path path5052Path = new Path();
        private static RectF path5054Rect = new RectF();
        private static Path path5054Path = new Path();
        private static RectF path5056Rect = new RectF();
        private static Path path5056Path = new Path();
        private static RectF path5015Rect = new RectF();
        private static Path path5015Path = new Path();
        private static RectF path5017Rect = new RectF();
        private static Path path5017Path = new Path();
        private static RectF path5019Rect = new RectF();
        private static Path path5019Path = new Path();
        private static RectF path5021Rect = new RectF();
        private static Path path5021Path = new Path();
        private static RectF path5023Rect = new RectF();
        private static Path path5023Path = new Path();
        private static RectF path5025Rect = new RectF();
        private static Path path5025Path = new Path();
        private static RectF path5027Rect = new RectF();
        private static Path path5027Path = new Path();
        private static RectF path5029Rect = new RectF();
        private static Path path5029Path = new Path();
        private static RectF path5031Rect = new RectF();
        private static Path path5031Path = new Path();
        private static RectF path5162Rect = new RectF();
        private static Path path5162Path = new Path();
        private static RectF path5164Rect = new RectF();
        private static Path path5164Path = new Path();
        private static RectF path5166Rect = new RectF();
        private static Path path5166Path = new Path();
        private static RectF path5168Rect = new RectF();
        private static Path path5168Path = new Path();
        private static RectF path5170Rect = new RectF();
        private static Path path5170Path = new Path();
        private static RectF path5172Rect = new RectF();
        private static Path path5172Path = new Path();
        private static RectF path5174Rect = new RectF();
        private static Path path5174Path = new Path();
        private static RectF path5176Rect = new RectF();
        private static Path path5176Path = new Path();
        private static RectF path5142Rect = new RectF();
        private static Path path5142Path = new Path();
        private static RectF path5106Rect = new RectF();
        private static Path path5106Path = new Path();
        private static RectF path5109Rect = new RectF();
        private static Path path5109Path = new Path();
        private static RectF path5111Rect = new RectF();
        private static Path path5111Path = new Path();
        private static RectF path5113Rect = new RectF();
        private static Path path5113Path = new Path();
        private static RectF path5115Rect = new RectF();
        private static Path path5115Path = new Path();
        private static RectF path5145Rect = new RectF();
        private static Path path5145Path = new Path();
    }

    public static void drawCanvas1(Canvas canvas) {
        FloorPlanHelper.drawCanvas1(canvas, new RectF(0f, 0f, 1869f, 2801f), ResizingBehavior.AspectFit);
    }

    public static void drawCanvas1(Canvas canvas, RectF targetFrame, ResizingBehavior resizing) {
        // General Declarations
        Paint paint = CacheForCanvas1.paint;

        // Local Colors
        int fillColor = Color.argb(49, 0, 0, 0);
        int strokeColor = Color.argb(255, 0, 0, 0);
        int fillColor6 = Color.argb(255, 0, 0, 0);
        int fillColor2 = Color.argb(255, 206, 30, 19);
        int fillColor3 = Color.argb(255, 152, 191, 36);
        int fillColor5 = Color.argb(255, 0, 128, 204);
        int fillColor4 = Color.argb(255, 255, 255, 255);

        // Resize to Target Frame
        canvas.save();
        RectF resizedFrame = CacheForCanvas1.resizedFrame;
        FloorPlanHelper.resizingBehaviorApply(resizing, CacheForCanvas1.originalFrame, targetFrame, resizedFrame);
        canvas.translate(resizedFrame.left, resizedFrame.top);
        canvas.scale(resizedFrame.width() / 1869f, resizedFrame.height() / 2801f);

        // beffroi2.svg Group
        {
            drawLayer1(paint, canvas);
            // layer1

            // layer2
            {
                // rect4707-6-1
                canvas.saveLayerAlpha(null, 252, Canvas.ALL_SAVE_FLAG);
                {
                    RectF rect470761Rect = CacheForCanvas1.rect470761Rect;
                    rect470761Rect.set(213.47f, 2716.01f, 298.81f, 2801.35f);
                    Path rect470761Path = CacheForCanvas1.rect470761Path;
                    rect470761Path.reset();
                    rect470761Path.moveTo(rect470761Rect.left, rect470761Rect.top);
                    rect470761Path.lineTo(rect470761Rect.right, rect470761Rect.top);
                    rect470761Path.lineTo(rect470761Rect.right, rect470761Rect.bottom);
                    rect470761Path.lineTo(rect470761Rect.left, rect470761Rect.bottom);
                    rect470761Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor2);
                    canvas.drawPath(rect470761Path, paint);
                }
                canvas.restore();

                // rect4707-6-6
                canvas.saveLayerAlpha(null, 252, Canvas.ALL_SAVE_FLAG);
                {
                    RectF rect470766Rect = CacheForCanvas1.rect470766Rect;
                    rect470766Rect.set(213.47f, 2605.59f, 298.81f, 2690.92f);
                    Path rect470766Path = CacheForCanvas1.rect470766Path;
                    rect470766Path.reset();
                    rect470766Path.moveTo(rect470766Rect.left, rect470766Rect.top);
                    rect470766Path.lineTo(rect470766Rect.right, rect470766Rect.top);
                    rect470766Path.lineTo(rect470766Rect.right, rect470766Rect.bottom);
                    rect470766Path.lineTo(rect470766Rect.left, rect470766Rect.bottom);
                    rect470766Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor2);
                    canvas.drawPath(rect470766Path, paint);
                }
                canvas.restore();

                // g1479
                {
                    // path1465
                    RectF path1465Rect = CacheForCanvas1.path1465Rect;
                    path1465Rect.set(511.3f, 237.46f, 567.6f, 293.76f);

                    // path1467
                    RectF path1467Rect = CacheForCanvas1.path1467Rect;
                    path1467Rect.set(520.67f, 242.13f, 560.54f, 289.04f);
                    Path path1467Path = CacheForCanvas1.path1467Path;
                    path1467Path.reset();
                    path1467Path.moveTo(524.19f, 289.04f);
                    path1467Path.lineTo(524.19f, 271.45f);
                    path1467Path.lineTo(520.67f, 271.45f);
                    path1467Path.lineTo(520.67f, 258.55f);
                    path1467Path.cubicTo(520.67f, 255.97f, 522.78f, 253.86f, 525.36f, 253.86f);
                    path1467Path.lineTo(532.4f, 253.86f);
                    path1467Path.cubicTo(534.98f, 253.86f, 537.09f, 255.97f, 537.09f, 258.55f);
                    path1467Path.lineTo(537.09f, 271.45f);
                    path1467Path.lineTo(533.57f, 271.45f);
                    path1467Path.lineTo(533.57f, 289.04f);
                    path1467Path.lineTo(524.19f, 289.04f);
                    path1467Path.close();
                    path1467Path.moveTo(553.51f, 289.04f);
                    path1467Path.lineTo(553.51f, 274.97f);
                    path1467Path.lineTo(560.54f, 274.97f);
                    path1467Path.lineTo(554.59f, 257.07f);
                    path1467Path.cubicTo(553.93f, 255.15f, 552.15f, 253.86f, 550.13f, 253.86f);
                    path1467Path.lineTo(549.85f, 253.86f);
                    path1467Path.cubicTo(547.83f, 253.86f, 546.03f, 255.15f, 545.39f, 257.07f);
                    path1467Path.lineTo(539.44f, 274.97f);
                    path1467Path.lineTo(546.47f, 274.97f);
                    path1467Path.lineTo(546.47f, 289.04f);
                    path1467Path.lineTo(553.51f, 289.04f);
                    path1467Path.close();
                    path1467Path.moveTo(528.88f, 251.51f);
                    path1467Path.cubicTo(531.49f, 251.51f, 533.57f, 249.43f, 533.57f, 246.82f);
                    path1467Path.cubicTo(533.57f, 244.22f, 531.49f, 242.13f, 528.88f, 242.13f);
                    path1467Path.cubicTo(526.28f, 242.13f, 524.19f, 244.22f, 524.19f, 246.82f);
                    path1467Path.cubicTo(524.19f, 249.43f, 526.28f, 251.51f, 528.88f, 251.51f);
                    path1467Path.close();
                    path1467Path.moveTo(549.99f, 251.51f);
                    path1467Path.cubicTo(552.59f, 251.51f, 554.68f, 249.43f, 554.68f, 246.82f);
                    path1467Path.cubicTo(554.68f, 244.22f, 552.59f, 242.13f, 549.99f, 242.13f);
                    path1467Path.cubicTo(547.39f, 242.13f, 545.3f, 244.22f, 545.3f, 246.82f);
                    path1467Path.cubicTo(545.3f, 249.43f, 547.39f, 251.51f, 549.99f, 251.51f);
                    path1467Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path1467Path, paint);
                }

                // g1479-3
                {
                    // path1465-4
                    RectF path14654Rect = CacheForCanvas1.path14654Rect;
                    path14654Rect.set(840.45f, 797.21f, 896.75f, 853.51f);

                    // path1467-4
                    RectF path14674Rect = CacheForCanvas1.path14674Rect;
                    path14674Rect.set(849.82f, 801.9f, 889.69f, 848.8f);
                    Path path14674Path = CacheForCanvas1.path14674Path;
                    path14674Path.reset();
                    path14674Path.moveTo(853.34f, 848.8f);
                    path14674Path.lineTo(853.34f, 831.22f);
                    path14674Path.lineTo(849.82f, 831.22f);
                    path14674Path.lineTo(849.82f, 818.32f);
                    path14674Path.cubicTo(849.82f, 815.74f, 851.93f, 813.63f, 854.51f, 813.63f);
                    path14674Path.lineTo(861.55f, 813.63f);
                    path14674Path.cubicTo(864.13f, 813.63f, 866.24f, 815.74f, 866.24f, 818.32f);
                    path14674Path.lineTo(866.24f, 831.22f);
                    path14674Path.lineTo(862.72f, 831.22f);
                    path14674Path.lineTo(862.72f, 848.8f);
                    path14674Path.lineTo(853.34f, 848.8f);
                    path14674Path.close();
                    path14674Path.moveTo(882.65f, 848.8f);
                    path14674Path.lineTo(882.65f, 834.73f);
                    path14674Path.lineTo(889.69f, 834.73f);
                    path14674Path.lineTo(883.73f, 816.84f);
                    path14674Path.cubicTo(883.07f, 814.92f, 881.29f, 813.63f, 879.28f, 813.63f);
                    path14674Path.lineTo(878.99f, 813.63f);
                    path14674Path.cubicTo(876.98f, 813.63f, 875.17f, 814.92f, 874.54f, 816.84f);
                    path14674Path.lineTo(868.58f, 834.73f);
                    path14674Path.lineTo(875.62f, 834.73f);
                    path14674Path.lineTo(875.62f, 848.8f);
                    path14674Path.lineTo(882.65f, 848.8f);
                    path14674Path.close();
                    path14674Path.moveTo(858.03f, 811.28f);
                    path14674Path.cubicTo(860.63f, 811.28f, 862.72f, 809.19f, 862.72f, 806.59f);
                    path14674Path.cubicTo(862.72f, 803.99f, 860.63f, 801.9f, 858.03f, 801.9f);
                    path14674Path.cubicTo(855.42f, 801.9f, 853.34f, 803.99f, 853.34f, 806.59f);
                    path14674Path.cubicTo(853.34f, 809.19f, 855.42f, 811.28f, 858.03f, 811.28f);
                    path14674Path.close();
                    path14674Path.moveTo(879.13f, 811.28f);
                    path14674Path.cubicTo(881.74f, 811.28f, 883.83f, 809.19f, 883.83f, 806.59f);
                    path14674Path.cubicTo(883.83f, 803.99f, 881.74f, 801.9f, 879.13f, 801.9f);
                    path14674Path.cubicTo(876.53f, 801.9f, 874.44f, 803.99f, 874.44f, 806.59f);
                    path14674Path.cubicTo(874.44f, 809.19f, 876.53f, 811.28f, 879.13f, 811.28f);
                    path14674Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path14674Path, paint);
                }

                // g1479-39
                {
                    // path1465-7
                    RectF path14657Rect = CacheForCanvas1.path14657Rect;
                    path14657Rect.set(699f, 1920.51f, 755.3f, 1976.81f);

                    // path1467-2
                    RectF path14672Rect = CacheForCanvas1.path14672Rect;
                    path14672Rect.set(708.4f, 1925.19f, 748.27f, 1972.09f);
                    Path path14672Path = CacheForCanvas1.path14672Path;
                    path14672Path.reset();
                    path14672Path.moveTo(711.92f, 1972.09f);
                    path14672Path.lineTo(711.92f, 1954.51f);
                    path14672Path.lineTo(708.4f, 1954.51f);
                    path14672Path.lineTo(708.4f, 1941.61f);
                    path14672Path.cubicTo(708.4f, 1939.03f, 710.51f, 1936.92f, 713.09f, 1936.92f);
                    path14672Path.lineTo(720.12f, 1936.92f);
                    path14672Path.cubicTo(722.7f, 1936.92f, 724.81f, 1939.03f, 724.81f, 1941.61f);
                    path14672Path.lineTo(724.81f, 1954.51f);
                    path14672Path.lineTo(721.3f, 1954.51f);
                    path14672Path.lineTo(721.3f, 1972.09f);
                    path14672Path.lineTo(711.92f, 1972.09f);
                    path14672Path.close();
                    path14672Path.moveTo(741.23f, 1972.09f);
                    path14672Path.lineTo(741.23f, 1958.02f);
                    path14672Path.lineTo(748.27f, 1958.02f);
                    path14672Path.lineTo(742.31f, 1940.13f);
                    path14672Path.cubicTo(741.65f, 1938.21f, 739.87f, 1936.92f, 737.85f, 1936.92f);
                    path14672Path.lineTo(737.57f, 1936.92f);
                    path14672Path.cubicTo(735.56f, 1936.92f, 733.75f, 1938.21f, 733.12f, 1940.13f);
                    path14672Path.lineTo(727.16f, 1958.02f);
                    path14672Path.lineTo(734.2f, 1958.02f);
                    path14672Path.lineTo(734.2f, 1972.09f);
                    path14672Path.lineTo(741.23f, 1972.09f);
                    path14672Path.close();
                    path14672Path.moveTo(716.61f, 1934.57f);
                    path14672Path.cubicTo(719.21f, 1934.57f, 721.3f, 1932.48f, 721.3f, 1929.88f);
                    path14672Path.cubicTo(721.3f, 1927.28f, 719.21f, 1925.19f, 716.61f, 1925.19f);
                    path14672Path.cubicTo(714f, 1925.19f, 711.92f, 1927.28f, 711.92f, 1929.88f);
                    path14672Path.cubicTo(711.92f, 1932.48f, 714f, 1934.57f, 716.61f, 1934.57f);
                    path14672Path.close();
                    path14672Path.moveTo(737.71f, 1934.57f);
                    path14672Path.cubicTo(740.32f, 1934.57f, 742.4f, 1932.48f, 742.4f, 1929.88f);
                    path14672Path.cubicTo(742.4f, 1927.28f, 740.32f, 1925.19f, 737.71f, 1925.19f);
                    path14672Path.cubicTo(735.11f, 1925.19f, 733.02f, 1927.28f, 733.02f, 1929.88f);
                    path14672Path.cubicTo(733.02f, 1932.48f, 735.11f, 1934.57f, 737.71f, 1934.57f);
                    path14672Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path14672Path, paint);
                }

                // flowRoot3269
                {
                    // path5127
                    RectF path5127Rect = CacheForCanvas1.path5127Rect;
                    path5127Rect.set(1023.69f, 775.32f, 1078.15f, 828.4f);
                    Path path5127Path = CacheForCanvas1.path5127Path;
                    path5127Path.reset();
                    path5127Path.moveTo(1035.64f, 775.32f);
                    path5127Path.lineTo(1050.96f, 816f);
                    path5127Path.lineTo(1066.23f, 775.32f);
                    path5127Path.lineTo(1078.15f, 775.32f);
                    path5127Path.lineTo(1078.15f, 828.4f);
                    path5127Path.lineTo(1068.97f, 828.4f);
                    path5127Path.lineTo(1068.97f, 810.9f);
                    path5127Path.lineTo(1069.88f, 787.49f);
                    path5127Path.lineTo(1054.2f, 828.4f);
                    path5127Path.lineTo(1047.6f, 828.4f);
                    path5127Path.lineTo(1031.96f, 787.53f);
                    path5127Path.lineTo(1032.87f, 810.9f);
                    path5127Path.lineTo(1032.87f, 828.4f);
                    path5127Path.lineTo(1023.69f, 828.4f);
                    path5127Path.lineTo(1023.69f, 775.32f);
                    path5127Path.lineTo(1035.64f, 775.32f);
                    path5127Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5127Path, paint);

                    // path5129
                    RectF path5129Rect = CacheForCanvas1.path5129Rect;
                    path5129Rect.set(1086.5f, 788.22f, 1123.18f, 829.13f);
                    Path path5129Path = CacheForCanvas1.path5129Path;
                    path5129Path.reset();
                    path5129Path.moveTo(1086.5f, 808.31f);
                    path5129Path.cubicTo(1086.5f, 804.45f, 1087.27f, 800.97f, 1088.8f, 797.88f);
                    path5129Path.cubicTo(1090.33f, 794.77f, 1092.48f, 792.39f, 1095.25f, 790.74f);
                    path5129Path.cubicTo(1098.02f, 789.06f, 1101.21f, 788.22f, 1104.81f, 788.22f);
                    path5129Path.cubicTo(1110.13f, 788.22f, 1114.44f, 789.94f, 1117.75f, 793.36f);
                    path5129Path.cubicTo(1121.08f, 796.79f, 1122.88f, 801.34f, 1123.14f, 807f);
                    path5129Path.lineTo(1123.18f, 809.08f);
                    path5129Path.cubicTo(1123.18f, 812.97f, 1122.43f, 816.44f, 1120.92f, 819.5f);
                    path5129Path.cubicTo(1119.44f, 822.57f, 1117.3f, 824.94f, 1114.5f, 826.61f);
                    path5129Path.cubicTo(1111.73f, 828.29f, 1108.52f, 829.13f, 1104.88f, 829.13f);
                    path5129Path.cubicTo(1099.31f, 829.13f, 1094.85f, 827.28f, 1091.5f, 823.59f);
                    path5129Path.cubicTo(1088.17f, 819.87f, 1086.5f, 814.92f, 1086.5f, 808.75f);
                    path5129Path.lineTo(1086.5f, 808.31f);
                    path5129Path.close();
                    path5129Path.moveTo(1095.36f, 809.08f);
                    path5129Path.cubicTo(1095.36f, 813.14f, 1096.2f, 816.32f, 1097.88f, 818.63f);
                    path5129Path.cubicTo(1099.56f, 820.91f, 1101.89f, 822.06f, 1104.88f, 822.06f);
                    path5129Path.cubicTo(1107.87f, 822.06f, 1110.19f, 820.89f, 1111.84f, 818.56f);
                    path5129Path.cubicTo(1113.52f, 816.22f, 1114.36f, 812.81f, 1114.36f, 808.31f);
                    path5129Path.cubicTo(1114.36f, 804.33f, 1113.49f, 801.17f, 1111.77f, 798.83f);
                    path5129Path.cubicTo(1110.07f, 796.5f, 1107.75f, 795.33f, 1104.81f, 795.33f);
                    path5129Path.cubicTo(1101.91f, 795.33f, 1099.62f, 796.49f, 1097.91f, 798.8f);
                    path5129Path.cubicTo(1096.21f, 801.08f, 1095.36f, 804.51f, 1095.36f, 809.08f);
                    path5129Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5129Path, paint);

                    // path5131
                    RectF path5131Rect = CacheForCanvas1.path5131Rect;
                    path5131Rect.set(1129.23f, 788.22f, 1163.94f, 829.13f);
                    Path path5131Path = CacheForCanvas1.path5131Path;
                    path5131Path.reset();
                    path5131Path.moveTo(1148.12f, 829.13f);
                    path5131Path.cubicTo(1142.5f, 829.13f, 1137.95f, 827.37f, 1134.45f, 823.84f);
                    path5131Path.cubicTo(1130.97f, 820.29f, 1129.23f, 815.58f, 1129.23f, 809.7f);
                    path5131Path.lineTo(1129.23f, 808.6f);
                    path5131Path.cubicTo(1129.23f, 804.67f, 1129.99f, 801.15f, 1131.49f, 798.07f);
                    path5131Path.cubicTo(1133.02f, 794.96f, 1135.16f, 792.54f, 1137.91f, 790.81f);
                    path5131Path.cubicTo(1140.66f, 789.09f, 1143.72f, 788.22f, 1147.1f, 788.22f);
                    path5131Path.cubicTo(1152.47f, 788.22f, 1156.61f, 789.94f, 1159.53f, 793.36f);
                    path5131Path.cubicTo(1162.47f, 796.79f, 1163.94f, 801.64f, 1163.94f, 807.91f);
                    path5131Path.lineTo(1163.94f, 811.48f);
                    path5131Path.lineTo(1138.16f, 811.48f);
                    path5131Path.cubicTo(1138.43f, 814.74f, 1139.51f, 817.32f, 1141.41f, 819.21f);
                    path5131Path.cubicTo(1143.33f, 821.11f, 1145.74f, 822.06f, 1148.63f, 822.06f);
                    path5131Path.cubicTo(1152.69f, 822.06f, 1155.99f, 820.42f, 1158.54f, 817.13f);
                    path5131Path.lineTo(1163.32f, 821.69f);
                    path5131Path.cubicTo(1161.74f, 824.05f, 1159.63f, 825.88f, 1156.98f, 827.2f);
                    path5131Path.cubicTo(1154.35f, 828.49f, 1151.4f, 829.13f, 1148.12f, 829.13f);
                    path5131Path.close();
                    path5131Path.moveTo(1147.06f, 795.33f);
                    path5131Path.cubicTo(1144.63f, 795.33f, 1142.66f, 796.18f, 1141.15f, 797.88f);
                    path5131Path.cubicTo(1139.67f, 799.59f, 1138.72f, 801.96f, 1138.31f, 804.99f);
                    path5131Path.lineTo(1155.19f, 804.99f);
                    path5131Path.lineTo(1155.19f, 804.34f);
                    path5131Path.cubicTo(1155f, 801.37f, 1154.21f, 799.14f, 1152.82f, 797.63f);
                    path5131Path.cubicTo(1151.44f, 796.1f, 1149.52f, 795.33f, 1147.06f, 795.33f);
                    path5131Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5131Path, paint);

                    // path5133
                    RectF path5133Rect = CacheForCanvas1.path5133Rect;
                    path5133Rect.set(1170.83f, 772.4f, 1205.47f, 829.13f);
                    Path path5133Path = CacheForCanvas1.path5133Path;
                    path5133Path.reset();
                    path5133Path.moveTo(1205.47f, 809.08f);
                    path5133Path.cubicTo(1205.47f, 815.2f, 1204.09f, 820.08f, 1201.35f, 823.7f);
                    path5133Path.cubicTo(1198.62f, 827.32f, 1194.87f, 829.13f, 1190.08f, 829.13f);
                    path5133Path.cubicTo(1185.46f, 829.13f, 1181.87f, 827.46f, 1179.29f, 824.13f);
                    path5133Path.lineTo(1178.85f, 828.4f);
                    path5133Path.lineTo(1170.83f, 828.4f);
                    path5133Path.lineTo(1170.83f, 772.4f);
                    path5133Path.lineTo(1179.69f, 772.4f);
                    path5133Path.lineTo(1179.69f, 792.74f);
                    path5133Path.cubicTo(1182.24f, 789.73f, 1185.68f, 788.22f, 1190.01f, 788.22f);
                    path5133Path.cubicTo(1194.82f, 788.22f, 1198.6f, 790.01f, 1201.35f, 793.58f);
                    path5133Path.cubicTo(1204.09f, 797.16f, 1205.47f, 802.15f, 1205.47f, 808.57f);
                    path5133Path.lineTo(1205.47f, 809.08f);
                    path5133Path.close();
                    path5133Path.moveTo(1196.61f, 808.31f);
                    path5133Path.cubicTo(1196.61f, 804.03f, 1195.85f, 800.83f, 1194.35f, 798.69f);
                    path5133Path.cubicTo(1192.84f, 796.55f, 1190.65f, 795.48f, 1187.78f, 795.48f);
                    path5133Path.cubicTo(1183.94f, 795.48f, 1181.25f, 797.16f, 1179.69f, 800.51f);
                    path5133Path.lineTo(1179.69f, 816.77f);
                    path5133Path.cubicTo(1181.27f, 820.2f, 1183.99f, 821.91f, 1187.86f, 821.91f);
                    path5133Path.cubicTo(1190.63f, 821.91f, 1192.77f, 820.88f, 1194.27f, 818.81f);
                    path5133Path.cubicTo(1195.78f, 816.75f, 1196.56f, 813.62f, 1196.61f, 809.44f);
                    path5133Path.lineTo(1196.61f, 808.31f);
                    path5133Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5133Path, paint);

                    // path5135
                    RectF path5135Rect = CacheForCanvas1.path5135Rect;
                    path5135Rect.set(1212.87f, 773.97f, 1222.89f, 828.4f);
                    Path path5135Path = CacheForCanvas1.path5135Path;
                    path5135Path.reset();
                    path5135Path.moveTo(1222.27f, 828.4f);
                    path5135Path.lineTo(1213.41f, 828.4f);
                    path5135Path.lineTo(1213.41f, 788.95f);
                    path5135Path.lineTo(1222.27f, 788.95f);
                    path5135Path.lineTo(1222.27f, 828.4f);
                    path5135Path.close();
                    path5135Path.moveTo(1212.87f, 778.71f);
                    path5135Path.cubicTo(1212.87f, 777.35f, 1213.29f, 776.22f, 1214.14f, 775.32f);
                    path5135Path.cubicTo(1215.02f, 774.42f, 1216.26f, 773.97f, 1217.86f, 773.97f);
                    path5135Path.cubicTo(1219.47f, 773.97f, 1220.71f, 774.42f, 1221.58f, 775.32f);
                    path5135Path.cubicTo(1222.46f, 776.22f, 1222.89f, 777.35f, 1222.89f, 778.71f);
                    path5135Path.cubicTo(1222.89f, 780.04f, 1222.46f, 781.16f, 1221.58f, 782.06f);
                    path5135Path.cubicTo(1220.71f, 782.94f, 1219.47f, 783.37f, 1217.86f, 783.37f);
                    path5135Path.cubicTo(1216.26f, 783.37f, 1215.02f, 782.94f, 1214.14f, 782.06f);
                    path5135Path.cubicTo(1213.29f, 781.16f, 1212.87f, 780.04f, 1212.87f, 778.71f);
                    path5135Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5135Path, paint);

                    // path5137
                    RectF path5137Rect = CacheForCanvas1.path5137Rect;
                    path5137Rect.set(1231.75f, 788.95f, 1264.42f, 829.13f);
                    Path path5137Path = CacheForCanvas1.path5137Path;
                    path5137Path.reset();
                    path5137Path.moveTo(1255.85f, 824.54f);
                    path5137Path.cubicTo(1253.25f, 827.6f, 1249.56f, 829.13f, 1244.77f, 829.13f);
                    path5137Path.cubicTo(1240.49f, 829.13f, 1237.25f, 827.88f, 1235.03f, 825.37f);
                    path5137Path.cubicTo(1232.85f, 822.87f, 1231.75f, 819.25f, 1231.75f, 814.51f);
                    path5137Path.lineTo(1231.75f, 788.95f);
                    path5137Path.lineTo(1240.61f, 788.95f);
                    path5137Path.lineTo(1240.61f, 814.4f);
                    path5137Path.cubicTo(1240.61f, 819.41f, 1242.69f, 821.91f, 1246.85f, 821.91f);
                    path5137Path.cubicTo(1251.15f, 821.91f, 1254.05f, 820.37f, 1255.56f, 817.28f);
                    path5137Path.lineTo(1255.56f, 788.95f);
                    path5137Path.lineTo(1264.42f, 788.95f);
                    path5137Path.lineTo(1264.42f, 828.4f);
                    path5137Path.lineTo(1256.07f, 828.4f);
                    path5137Path.lineTo(1255.85f, 824.54f);
                    path5137Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5137Path, paint);

                    // path5139
                    RectF path5139Rect = CacheForCanvas1.path5139Rect;
                    path5139Rect.set(1271.71f, 788.22f, 1304.34f, 829.13f);
                    Path path5139Path = CacheForCanvas1.path5139Path;
                    path5139Path.reset();
                    path5139Path.moveTo(1295.63f, 817.68f);
                    path5139Path.cubicTo(1295.63f, 816.1f, 1294.97f, 814.9f, 1293.66f, 814.07f);
                    path5139Path.cubicTo(1292.37f, 813.25f, 1290.22f, 812.52f, 1287.21f, 811.88f);
                    path5139Path.cubicTo(1284.19f, 811.25f, 1281.68f, 810.45f, 1279.66f, 809.48f);
                    path5139Path.cubicTo(1275.24f, 807.34f, 1273.02f, 804.24f, 1273.02f, 800.18f);
                    path5139Path.cubicTo(1273.02f, 796.78f, 1274.46f, 793.93f, 1277.33f, 791.65f);
                    path5139Path.cubicTo(1280.19f, 789.37f, 1283.84f, 788.22f, 1288.26f, 788.22f);
                    path5139Path.cubicTo(1292.98f, 788.22f, 1296.78f, 789.39f, 1299.68f, 791.72f);
                    path5139Path.cubicTo(1302.59f, 794.06f, 1304.05f, 797.08f, 1304.05f, 800.8f);
                    path5139Path.lineTo(1295.19f, 800.8f);
                    path5139Path.cubicTo(1295.19f, 799.1f, 1294.56f, 797.69f, 1293.29f, 796.57f);
                    path5139Path.cubicTo(1292.03f, 795.43f, 1290.35f, 794.86f, 1288.26f, 794.86f);
                    path5139Path.cubicTo(1286.32f, 794.86f, 1284.73f, 795.31f, 1283.49f, 796.21f);
                    path5139Path.cubicTo(1282.27f, 797.11f, 1281.66f, 798.31f, 1281.66f, 799.82f);
                    path5139Path.cubicTo(1281.66f, 801.18f, 1282.24f, 802.24f, 1283.38f, 802.99f);
                    path5139Path.cubicTo(1284.52f, 803.74f, 1286.83f, 804.51f, 1290.31f, 805.29f);
                    path5139Path.cubicTo(1293.78f, 806.04f, 1296.5f, 806.95f, 1298.47f, 808.02f);
                    path5139Path.cubicTo(1300.47f, 809.07f, 1301.94f, 810.33f, 1302.88f, 811.81f);
                    path5139Path.cubicTo(1303.86f, 813.29f, 1304.34f, 815.09f, 1304.34f, 817.21f);
                    path5139Path.cubicTo(1304.34f, 820.76f, 1302.87f, 823.64f, 1299.93f, 825.85f);
                    path5139Path.cubicTo(1296.99f, 828.04f, 1293.14f, 829.13f, 1288.37f, 829.13f);
                    path5139Path.cubicTo(1285.14f, 829.13f, 1282.26f, 828.55f, 1279.73f, 827.38f);
                    path5139Path.cubicTo(1277.2f, 826.21f, 1275.24f, 824.61f, 1273.83f, 822.57f);
                    path5139Path.cubicTo(1272.42f, 820.53f, 1271.71f, 818.33f, 1271.71f, 815.97f);
                    path5139Path.lineTo(1280.32f, 815.97f);
                    path5139Path.cubicTo(1280.44f, 818.06f, 1281.23f, 819.67f, 1282.69f, 820.82f);
                    path5139Path.cubicTo(1284.14f, 821.93f, 1286.08f, 822.49f, 1288.48f, 822.49f);
                    path5139Path.cubicTo(1290.82f, 822.49f, 1292.59f, 822.06f, 1293.81f, 821.18f);
                    path5139Path.cubicTo(1295.02f, 820.28f, 1295.63f, 819.12f, 1295.63f, 817.68f);
                    path5139Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5139Path, paint);
                }

                // flowRoot3269-5
                {
                    // path5118
                    RectF path5118Rect = CacheForCanvas1.path5118Rect;
                    path5118Rect.set(1050.93f, 1909.83f, 1088.81f, 1962.92f);
                    Path path5118Path = CacheForCanvas1.path5118Path;
                    path5118Path.reset();
                    path5118Path.moveTo(1050.93f, 1962.92f);
                    path5118Path.lineTo(1050.93f, 1909.83f);
                    path5118Path.lineTo(1069.12f, 1909.83f);
                    path5118Path.cubicTo(1075.13f, 1909.83f, 1079.7f, 1911.04f, 1082.83f, 1913.44f);
                    path5118Path.cubicTo(1085.97f, 1915.85f, 1087.53f, 1919.44f, 1087.53f, 1924.2f);
                    path5118Path.cubicTo(1087.53f, 1926.63f, 1086.88f, 1928.82f, 1085.57f, 1930.76f);
                    path5118Path.cubicTo(1084.25f, 1932.71f, 1082.33f, 1934.21f, 1079.81f, 1935.28f);
                    path5118Path.cubicTo(1082.67f, 1936.06f, 1084.89f, 1937.53f, 1086.44f, 1939.69f);
                    path5118Path.cubicTo(1088.02f, 1941.83f, 1088.81f, 1944.41f, 1088.81f, 1947.42f);
                    path5118Path.cubicTo(1088.81f, 1952.41f, 1087.21f, 1956.23f, 1084f, 1958.91f);
                    path5118Path.cubicTo(1080.81f, 1961.58f, 1076.24f, 1962.92f, 1070.29f, 1962.92f);
                    path5118Path.lineTo(1050.93f, 1962.92f);
                    path5118Path.close();
                    path5118Path.moveTo(1060.15f, 1938.96f);
                    path5118Path.lineTo(1060.15f, 1955.55f);
                    path5118Path.lineTo(1070.4f, 1955.55f);
                    path5118Path.cubicTo(1073.29f, 1955.55f, 1075.55f, 1954.84f, 1077.18f, 1953.4f);
                    path5118Path.cubicTo(1078.81f, 1951.97f, 1079.62f, 1949.98f, 1079.62f, 1947.42f);
                    path5118Path.cubicTo(1079.62f, 1941.91f, 1076.8f, 1939.09f, 1071.17f, 1938.96f);
                    path5118Path.lineTo(1060.15f, 1938.96f);
                    path5118Path.close();
                    path5118Path.moveTo(1060.15f, 1932.18f);
                    path5118Path.lineTo(1069.2f, 1932.18f);
                    path5118Path.cubicTo(1072.06f, 1932.18f, 1074.3f, 1931.54f, 1075.9f, 1930.25f);
                    path5118Path.cubicTo(1077.53f, 1928.94f, 1078.35f, 1927.09f, 1078.35f, 1924.71f);
                    path5118Path.cubicTo(1078.35f, 1922.08f, 1077.59f, 1920.19f, 1076.09f, 1919.02f);
                    path5118Path.cubicTo(1074.6f, 1917.86f, 1072.28f, 1917.27f, 1069.12f, 1917.27f);
                    path5118Path.lineTo(1060.15f, 1917.27f);
                    path5118Path.lineTo(1060.15f, 1932.18f);
                    path5118Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5118Path, paint);

                    // path5120
                    RectF path5120Rect = CacheForCanvas1.path5120Rect;
                    path5120Rect.set(1097.72f, 1906.9f, 1106.58f, 1962.9f);
                    Path path5120Path = CacheForCanvas1.path5120Path;
                    path5120Path.reset();
                    path5120Path.moveTo(path5120Rect.left, path5120Rect.top);
                    path5120Path.lineTo(path5120Rect.right, path5120Rect.top);
                    path5120Path.lineTo(path5120Rect.right, path5120Rect.bottom);
                    path5120Path.lineTo(path5120Rect.left, path5120Rect.bottom);
                    path5120Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5120Path, paint);

                    // path5122
                    RectF path5122Rect = CacheForCanvas1.path5122Rect;
                    path5122Rect.set(1116.3f, 1908.49f, 1126.33f, 1962.92f);
                    Path path5122Path = CacheForCanvas1.path5122Path;
                    path5122Path.reset();
                    path5122Path.moveTo(1125.71f, 1962.92f);
                    path5122Path.lineTo(1116.85f, 1962.92f);
                    path5122Path.lineTo(1116.85f, 1923.47f);
                    path5122Path.lineTo(1125.71f, 1923.47f);
                    path5122Path.lineTo(1125.71f, 1962.92f);
                    path5122Path.close();
                    path5122Path.moveTo(1116.3f, 1913.23f);
                    path5122Path.cubicTo(1116.3f, 1911.86f, 1116.73f, 1910.73f, 1117.58f, 1909.83f);
                    path5122Path.cubicTo(1118.45f, 1908.94f, 1119.69f, 1908.49f, 1121.3f, 1908.49f);
                    path5122Path.cubicTo(1122.9f, 1908.49f, 1124.14f, 1908.94f, 1125.01f, 1909.83f);
                    path5122Path.cubicTo(1125.89f, 1910.73f, 1126.33f, 1911.86f, 1126.33f, 1913.23f);
                    path5122Path.cubicTo(1126.33f, 1914.56f, 1125.89f, 1915.68f, 1125.01f, 1916.58f);
                    path5122Path.cubicTo(1124.14f, 1917.45f, 1122.9f, 1917.89f, 1121.3f, 1917.89f);
                    path5122Path.cubicTo(1119.69f, 1917.89f, 1118.45f, 1917.45f, 1117.58f, 1916.58f);
                    path5122Path.cubicTo(1116.73f, 1915.68f, 1116.3f, 1914.56f, 1116.3f, 1913.23f);
                    path5122Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5122Path, paint);

                    // path5124
                    RectF path5124Rect = CacheForCanvas1.path5124Rect;
                    path5124Rect.set(1135.26f, 1922.74f, 1167.89f, 1962.92f);
                    Path path5124Path = CacheForCanvas1.path5124Path;
                    path5124Path.reset();
                    path5124Path.moveTo(1143.61f, 1923.47f);
                    path5124Path.lineTo(1143.86f, 1928.03f);
                    path5124Path.cubicTo(1146.78f, 1924.5f, 1150.61f, 1922.74f, 1155.35f, 1922.74f);
                    path5124Path.cubicTo(1163.56f, 1922.74f, 1167.74f, 1927.44f, 1167.89f, 1936.85f);
                    path5124Path.lineTo(1167.89f, 1962.92f);
                    path5124Path.lineTo(1159.03f, 1962.92f);
                    path5124Path.lineTo(1159.03f, 1937.36f);
                    path5124Path.cubicTo(1159.03f, 1934.86f, 1158.48f, 1933.01f, 1157.39f, 1931.82f);
                    path5124Path.cubicTo(1156.32f, 1930.6f, 1154.56f, 1930f, 1152.1f, 1930f);
                    path5124Path.cubicTo(1148.53f, 1930f, 1145.87f, 1931.61f, 1144.12f, 1934.85f);
                    path5124Path.lineTo(1144.12f, 1962.92f);
                    path5124Path.lineTo(1135.26f, 1962.92f);
                    path5124Path.lineTo(1135.26f, 1923.47f);
                    path5124Path.lineTo(1143.61f, 1923.47f);
                    path5124Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5124Path, paint);
                }

                // flowRoot3269-5-2
                {
                    // path5148
                    RectF path5148Rect = CacheForCanvas1.path5148Rect;
                    path5148Rect.set(269.07f, 27.98f, 300.21f, 73.62f);
                    Path path5148Path = CacheForCanvas1.path5148Path;
                    path5148Path.reset();
                    path5148Path.moveTo(300.21f, 73.62f);
                    path5148Path.lineTo(269.94f, 73.62f);
                    path5148Path.lineTo(269.94f, 68.45f);
                    path5148Path.lineTo(284.93f, 52.1f);
                    path5148Path.cubicTo(287.1f, 49.69f, 288.63f, 47.68f, 289.54f, 46.07f);
                    path5148Path.cubicTo(290.47f, 44.44f, 290.93f, 42.81f, 290.93f, 41.18f);
                    path5148Path.cubicTo(290.93f, 39.04f, 290.32f, 37.31f, 289.11f, 35.99f);
                    path5148Path.cubicTo(287.91f, 34.67f, 286.29f, 34.01f, 284.25f, 34.01f);
                    path5148Path.cubicTo(281.82f, 34.01f, 279.93f, 34.75f, 278.59f, 36.24f);
                    path5148Path.cubicTo(277.25f, 37.72f, 276.58f, 39.75f, 276.58f, 42.33f);
                    path5148Path.lineTo(269.07f, 42.33f);
                    path5148Path.cubicTo(269.07f, 39.59f, 269.69f, 37.12f, 270.93f, 34.94f);
                    path5148Path.cubicTo(272.18f, 32.73f, 273.97f, 31.02f, 276.27f, 29.8f);
                    path5148Path.cubicTo(278.6f, 28.59f, 281.28f, 27.98f, 284.31f, 27.98f);
                    path5148Path.cubicTo(288.68f, 27.98f, 292.13f, 29.08f, 294.64f, 31.29f);
                    path5148Path.cubicTo(297.18f, 33.47f, 298.44f, 36.5f, 298.44f, 40.38f);
                    path5148Path.cubicTo(298.44f, 42.63f, 297.8f, 44.99f, 296.53f, 47.46f);
                    path5148Path.cubicTo(295.27f, 49.91f, 293.2f, 52.71f, 290.31f, 55.84f);
                    path5148Path.lineTo(279.3f, 67.62f);
                    path5148Path.lineTo(300.21f, 67.62f);
                    path5148Path.lineTo(300.21f, 73.62f);
                    path5148Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5148Path, paint);

                    // path5150
                    RectF path5150Rect = CacheForCanvas1.path5150Rect;
                    path5150Rect.set(306.73f, 65.7f, 315.48f, 73.96f);
                    Path path5150Path = CacheForCanvas1.path5150Path;
                    path5150Path.reset();
                    path5150Path.moveTo(311.09f, 65.7f);
                    path5150Path.cubicTo(312.49f, 65.7f, 313.57f, 66.1f, 314.34f, 66.91f);
                    path5150Path.cubicTo(315.1f, 67.69f, 315.48f, 68.68f, 315.48f, 69.88f);
                    path5150Path.cubicTo(315.48f, 71.05f, 315.1f, 72.03f, 314.34f, 72.81f);
                    path5150Path.cubicTo(313.57f, 73.58f, 312.49f, 73.96f, 311.09f, 73.96f);
                    path5150Path.cubicTo(309.75f, 73.96f, 308.69f, 73.58f, 307.9f, 72.81f);
                    path5150Path.cubicTo(307.12f, 72.05f, 306.73f, 71.07f, 306.73f, 69.88f);
                    path5150Path.cubicTo(306.73f, 68.68f, 307.11f, 67.69f, 307.87f, 66.91f);
                    path5150Path.cubicTo(308.64f, 66.1f, 309.71f, 65.7f, 311.09f, 65.7f);
                    path5150Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5150Path, paint);

                    // path5152
                    RectF path5152Rect = CacheForCanvas1.path5152Rect;
                    path5152Rect.set(321.85f, 28.6f, 354.62f, 73.62f);
                    Path path5152Path = CacheForCanvas1.path5152Path;
                    path5152Path.reset();
                    path5152Path.moveTo(349.03f, 57.57f);
                    path5152Path.lineTo(354.62f, 57.57f);
                    path5152Path.lineTo(354.62f, 63.6f);
                    path5152Path.lineTo(349.03f, 63.6f);
                    path5152Path.lineTo(349.03f, 73.62f);
                    path5152Path.lineTo(341.51f, 73.62f);
                    path5152Path.lineTo(341.51f, 63.6f);
                    path5152Path.lineTo(322.07f, 63.6f);
                    path5152Path.lineTo(321.85f, 59.02f);
                    path5152Path.lineTo(341.27f, 28.6f);
                    path5152Path.lineTo(349.03f, 28.6f);
                    path5152Path.lineTo(349.03f, 57.57f);
                    path5152Path.close();
                    path5152Path.moveTo(329.73f, 57.57f);
                    path5152Path.lineTo(341.51f, 57.57f);
                    path5152Path.lineTo(341.51f, 38.77f);
                    path5152Path.lineTo(340.96f, 39.76f);
                    path5152Path.lineTo(329.73f, 57.57f);
                    path5152Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5152Path, paint);
                }

                // flowRoot3269-5-2-5
                {
                    // path5155
                    RectF path5155Rect = CacheForCanvas1.path5155Rect;
                    path5155Rect.set(173.36f, 235.55f, 204.5f, 281.19f);
                    Path path5155Path = CacheForCanvas1.path5155Path;
                    path5155Path.reset();
                    path5155Path.moveTo(204.5f, 281.19f);
                    path5155Path.lineTo(174.23f, 281.19f);
                    path5155Path.lineTo(174.23f, 276.02f);
                    path5155Path.lineTo(189.22f, 259.67f);
                    path5155Path.cubicTo(191.39f, 257.25f, 192.92f, 255.24f, 193.83f, 253.64f);
                    path5155Path.cubicTo(194.76f, 252.01f, 195.22f, 250.38f, 195.22f, 248.75f);
                    path5155Path.cubicTo(195.22f, 246.61f, 194.61f, 244.88f, 193.4f, 243.56f);
                    path5155Path.cubicTo(192.2f, 242.24f, 190.58f, 241.58f, 188.54f, 241.58f);
                    path5155Path.cubicTo(186.11f, 241.58f, 184.22f, 242.32f, 182.88f, 243.8f);
                    path5155Path.cubicTo(181.54f, 245.29f, 180.87f, 247.32f, 180.87f, 249.9f);
                    path5155Path.lineTo(173.36f, 249.9f);
                    path5155Path.cubicTo(173.36f, 247.15f, 173.98f, 244.69f, 175.22f, 242.51f);
                    path5155Path.cubicTo(176.47f, 240.3f, 178.26f, 238.59f, 180.57f, 237.37f);
                    path5155Path.cubicTo(182.89f, 236.16f, 185.57f, 235.55f, 188.6f, 235.55f);
                    path5155Path.cubicTo(192.97f, 235.55f, 196.42f, 236.65f, 198.93f, 238.86f);
                    path5155Path.cubicTo(201.47f, 241.04f, 202.73f, 244.07f, 202.73f, 247.95f);
                    path5155Path.cubicTo(202.73f, 250.19f, 202.1f, 252.55f, 200.82f, 255.03f);
                    path5155Path.cubicTo(199.56f, 257.48f, 197.49f, 260.27f, 194.6f, 263.41f);
                    path5155Path.lineTo(183.6f, 275.19f);
                    path5155Path.lineTo(204.5f, 275.19f);
                    path5155Path.lineTo(204.5f, 281.19f);
                    path5155Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5155Path, paint);

                    // path5157
                    RectF path5157Rect = CacheForCanvas1.path5157Rect;
                    path5157Rect.set(211.02f, 273.27f, 219.77f, 281.53f);
                    Path path5157Path = CacheForCanvas1.path5157Path;
                    path5157Path.reset();
                    path5157Path.moveTo(215.38f, 273.27f);
                    path5157Path.cubicTo(216.78f, 273.27f, 217.86f, 273.67f, 218.63f, 274.48f);
                    path5157Path.cubicTo(219.39f, 275.26f, 219.77f, 276.25f, 219.77f, 277.44f);
                    path5157Path.cubicTo(219.77f, 278.62f, 219.39f, 279.6f, 218.63f, 280.38f);
                    path5157Path.cubicTo(217.86f, 281.14f, 216.78f, 281.53f, 215.38f, 281.53f);
                    path5157Path.cubicTo(214.04f, 281.53f, 212.98f, 281.14f, 212.2f, 280.38f);
                    path5157Path.cubicTo(211.41f, 279.62f, 211.02f, 278.64f, 211.02f, 277.44f);
                    path5157Path.cubicTo(211.02f, 276.25f, 211.4f, 275.26f, 212.16f, 274.48f);
                    path5157Path.cubicTo(212.93f, 273.67f, 214f, 273.27f, 215.38f, 273.27f);
                    path5157Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5157Path, paint);

                    // path5159
                    RectF path5159Rect = CacheForCanvas1.path5159Rect;
                    path5159Rect.set(227.04f, 235.55f, 258.17f, 281.19f);
                    Path path5159Path = CacheForCanvas1.path5159Path;
                    path5159Path.reset();
                    path5159Path.moveTo(258.17f, 281.19f);
                    path5159Path.lineTo(227.9f, 281.19f);
                    path5159Path.lineTo(227.9f, 276.02f);
                    path5159Path.lineTo(242.9f, 259.67f);
                    path5159Path.cubicTo(245.06f, 257.25f, 246.6f, 255.24f, 247.51f, 253.64f);
                    path5159Path.cubicTo(248.43f, 252.01f, 248.9f, 250.38f, 248.9f, 248.75f);
                    path5159Path.cubicTo(248.9f, 246.61f, 248.29f, 244.88f, 247.07f, 243.56f);
                    path5159Path.cubicTo(245.88f, 242.24f, 244.26f, 241.58f, 242.22f, 241.58f);
                    path5159Path.cubicTo(239.79f, 241.58f, 237.9f, 242.32f, 236.56f, 243.8f);
                    path5159Path.cubicTo(235.22f, 245.29f, 234.55f, 247.32f, 234.55f, 249.9f);
                    path5159Path.lineTo(227.04f, 249.9f);
                    path5159Path.cubicTo(227.04f, 247.15f, 227.66f, 244.69f, 228.89f, 242.51f);
                    path5159Path.cubicTo(230.15f, 240.3f, 231.93f, 238.59f, 234.24f, 237.37f);
                    path5159Path.cubicTo(236.57f, 236.16f, 239.25f, 235.55f, 242.28f, 235.55f);
                    path5159Path.cubicTo(246.65f, 235.55f, 250.09f, 236.65f, 252.61f, 238.86f);
                    path5159Path.cubicTo(255.14f, 241.04f, 256.41f, 244.07f, 256.41f, 247.95f);
                    path5159Path.cubicTo(256.41f, 250.19f, 255.77f, 252.55f, 254.49f, 255.03f);
                    path5159Path.cubicTo(253.24f, 257.48f, 251.16f, 260.27f, 248.28f, 263.41f);
                    path5159Path.lineTo(237.27f, 275.19f);
                    path5159Path.lineTo(258.17f, 275.19f);
                    path5159Path.lineTo(258.17f, 281.19f);
                    path5159Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5159Path, paint);
                }

                // g3363
                {
                    // path3349
                    RectF path3349Rect = CacheForCanvas1.path3349Rect;
                    path3349Rect.set(248.21f, 963.77f, 295f, 1010.53f);
                    Path path3349Path = CacheForCanvas1.path3349Path;
                    path3349Path.reset();
                    path3349Path.moveTo(284.49f, 1010.53f);
                    path3349Path.lineTo(288.02f, 1010.53f);
                    path3349Path.cubicTo(289.81f, 1010.53f, 291.27f, 1009.17f, 291.49f, 1007.43f);
                    path3349Path.lineTo(295f, 972.38f);
                    path3349Path.lineTo(284.36f, 972.38f);
                    path3349Path.lineTo(284.36f, 963.77f);
                    path3349Path.lineTo(280.17f, 963.77f);
                    path3349Path.lineTo(280.17f, 972.38f);
                    path3349Path.lineTo(269.6f, 972.38f);
                    path3349Path.lineTo(270.24f, 977.36f);
                    path3349Path.cubicTo(273.88f, 978.36f, 277.28f, 980.16f, 279.32f, 982.16f);
                    path3349Path.cubicTo(282.38f, 985.18f, 284.49f, 988.31f, 284.49f, 993.41f);
                    path3349Path.lineTo(284.49f, 1010.53f);
                    path3349Path.close();
                    path3349Path.moveTo(248.21f, 1008.41f);
                    path3349Path.lineTo(248.21f, 1006.3f);
                    path3349Path.lineTo(280.17f, 1006.3f);
                    path3349Path.lineTo(280.17f, 1008.41f);
                    path3349Path.cubicTo(280.17f, 1009.58f, 279.22f, 1010.53f, 278.02f, 1010.53f);
                    path3349Path.lineTo(250.36f, 1010.53f);
                    path3349Path.cubicTo(249.17f, 1010.53f, 248.21f, 1009.58f, 248.21f, 1008.41f);
                    path3349Path.close();
                    path3349Path.moveTo(280.17f, 993.52f);
                    path3349Path.cubicTo(280.17f, 976.51f, 248.21f, 976.51f, 248.21f, 993.52f);
                    path3349Path.lineTo(280.17f, 993.52f);
                    path3349Path.close();
                    path3349Path.moveTo(248.25f, 997.79f);
                    path3349Path.lineTo(280.15f, 997.79f);
                    path3349Path.lineTo(280.15f, 1002.05f);
                    path3349Path.lineTo(248.25f, 1002.05f);
                    path3349Path.lineTo(248.25f, 997.79f);
                    path3349Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path3349Path, paint);

                    // path3351
                    RectF path3351Rect = CacheForCanvas1.path3351Rect;
                    path3351Rect.set(246.08f, 961.63f, 297.13f, 1012.68f);
                }

                // g3363-3
                {
                    // path3349-3
                    RectF path33493Rect = CacheForCanvas1.path33493Rect;
                    path33493Rect.set(541.05f, 649.34f, 585.98f, 694.26f);
                    Path path33493Path = CacheForCanvas1.path33493Path;
                    path33493Path.reset();
                    path33493Path.moveTo(575.89f, 694.26f);
                    path33493Path.lineTo(579.28f, 694.26f);
                    path33493Path.cubicTo(581f, 694.26f, 582.41f, 692.95f, 582.61f, 691.27f);
                    path33493Path.lineTo(585.98f, 657.61f);
                    path33493Path.lineTo(575.77f, 657.61f);
                    path33493Path.lineTo(575.77f, 649.34f);
                    path33493Path.lineTo(571.75f, 649.34f);
                    path33493Path.lineTo(571.75f, 657.61f);
                    path33493Path.lineTo(561.6f, 657.61f);
                    path33493Path.lineTo(562.21f, 662.39f);
                    path33493Path.cubicTo(565.7f, 663.35f, 568.97f, 665.09f, 570.93f, 667.01f);
                    path33493Path.cubicTo(573.87f, 669.91f, 575.89f, 672.91f, 575.89f, 677.81f);
                    path33493Path.lineTo(575.89f, 694.26f);
                    path33493Path.close();
                    path33493Path.moveTo(541.05f, 692.21f);
                    path33493Path.lineTo(541.05f, 690.19f);
                    path33493Path.lineTo(571.75f, 690.19f);
                    path33493Path.lineTo(571.75f, 692.21f);
                    path33493Path.cubicTo(571.75f, 693.34f, 570.83f, 694.26f, 569.68f, 694.26f);
                    path33493Path.lineTo(543.11f, 694.26f);
                    path33493Path.cubicTo(541.97f, 694.26f, 541.05f, 693.34f, 541.05f, 692.21f);
                    path33493Path.close();
                    path33493Path.moveTo(571.75f, 677.92f);
                    path33493Path.cubicTo(571.75f, 661.58f, 541.05f, 661.58f, 541.05f, 677.92f);
                    path33493Path.lineTo(571.75f, 677.92f);
                    path33493Path.close();
                    path33493Path.moveTo(541.09f, 682.02f);
                    path33493Path.lineTo(571.73f, 682.02f);
                    path33493Path.lineTo(571.73f, 686.11f);
                    path33493Path.lineTo(541.09f, 686.11f);
                    path33493Path.lineTo(541.09f, 682.02f);
                    path33493Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path33493Path, paint);

                    // path3351-8
                    RectF path33518Rect = CacheForCanvas1.path33518Rect;
                    path33518Rect.set(539f, 647.28f, 588f, 696.28f);
                }

                // g3411
                {
                    // path3397
                    RectF path3397Rect = CacheForCanvas1.path3397Rect;
                    path3397Rect.set(435.76f, 610.14f, 479.47f, 653.85f);
                    Path path3397Path = CacheForCanvas1.path3397Path;
                    path3397Path.reset();
                    path3397Path.moveTo(479.47f, 615f);
                    path3397Path.lineTo(479.47f, 610.14f);
                    path3397Path.lineTo(435.76f, 610.14f);
                    path3397Path.lineTo(435.76f, 615f);
                    path3397Path.lineTo(455.19f, 636.85f);
                    path3397Path.lineTo(455.19f, 649f);
                    path3397Path.lineTo(443.05f, 649f);
                    path3397Path.lineTo(443.05f, 653.85f);
                    path3397Path.lineTo(472.19f, 653.85f);
                    path3397Path.lineTo(472.19f, 649f);
                    path3397Path.lineTo(460.05f, 649f);
                    path3397Path.lineTo(460.05f, 636.85f);
                    path3397Path.lineTo(479.47f, 615f);
                    path3397Path.close();
                    path3397Path.moveTo(446.52f, 619.85f);
                    path3397Path.lineTo(442.22f, 615f);
                    path3397Path.lineTo(473.04f, 615f);
                    path3397Path.lineTo(468.72f, 619.85f);
                    path3397Path.lineTo(446.52f, 619.85f);
                    path3397Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path3397Path, paint);

                    // path3399
                    RectF path3399Rect = CacheForCanvas1.path3399Rect;
                    path3399Rect.set(428.45f, 602.86f, 486.75f, 661.16f);
                }

                // g3427
                {
                    // path3413
                    RectF path3413Rect = CacheForCanvas1.path3413Rect;
                    path3413Rect.set(256.85f, 1439.26f, 315.15f, 1497.56f);

                    // path3415
                    RectF path3415Rect = CacheForCanvas1.path3415Rect;
                    path3415Rect.set(261.71f, 1444.09f, 310.28f, 1492.66f);
                    Path path3415Path = CacheForCanvas1.path3415Path;
                    path3415Path.reset();
                    path3415Path.moveTo(308.85f, 1467.36f);
                    path3415Path.lineTo(286.99f, 1445.5f);
                    path3415Path.cubicTo(286.12f, 1444.62f, 284.9f, 1444.09f, 283.57f, 1444.09f);
                    path3415Path.lineTo(266.57f, 1444.09f);
                    path3415Path.cubicTo(263.9f, 1444.09f, 261.71f, 1446.28f, 261.71f, 1448.95f);
                    path3415Path.lineTo(261.71f, 1465.95f);
                    path3415Path.cubicTo(261.71f, 1467.28f, 262.25f, 1468.5f, 263.14f, 1469.4f);
                    path3415Path.lineTo(285f, 1491.25f);
                    path3415Path.cubicTo(285.88f, 1492.13f, 287.09f, 1492.66f, 288.43f, 1492.66f);
                    path3415Path.cubicTo(289.76f, 1492.66f, 290.98f, 1492.13f, 291.85f, 1491.23f);
                    path3415Path.lineTo(308.85f, 1474.23f);
                    path3415Path.cubicTo(309.75f, 1473.35f, 310.28f, 1472.14f, 310.28f, 1470.8f);
                    path3415Path.cubicTo(310.28f, 1469.47f, 309.72f, 1468.23f, 308.85f, 1467.36f);
                    path3415Path.close();
                    path3415Path.moveTo(270.21f, 1456.23f);
                    path3415Path.cubicTo(268.2f, 1456.23f, 266.57f, 1454.61f, 266.57f, 1452.59f);
                    path3415Path.cubicTo(266.57f, 1450.57f, 268.2f, 1448.95f, 270.21f, 1448.95f);
                    path3415Path.cubicTo(272.23f, 1448.95f, 273.85f, 1450.57f, 273.85f, 1452.59f);
                    path3415Path.cubicTo(273.85f, 1454.61f, 272.23f, 1456.23f, 270.21f, 1456.23f);
                    path3415Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path3415Path, paint);
                }

                // g3443
                {
                    // path3429
                    RectF path3429Rect = CacheForCanvas1.path3429Rect;
                    path3429Rect.set(245.63f, 814.28f, 306.77f, 875.43f);

                    // path3431
                    RectF path3431Rect = CacheForCanvas1.path3431Rect;
                    path3431Rect.set(255.81f, 821.92f, 301.66f, 867.78f);
                    Path path3431Path = CacheForCanvas1.path3431Path;
                    path3431Path.reset();
                    path3431Path.moveTo(296.57f, 821.92f);
                    path3431Path.lineTo(255.81f, 821.92f);
                    path3431Path.lineTo(255.81f, 847.4f);
                    path3431Path.cubicTo(255.81f, 853.03f, 260.37f, 857.59f, 266f, 857.59f);
                    path3431Path.lineTo(281.28f, 857.59f);
                    path3431Path.cubicTo(286.91f, 857.59f, 291.47f, 853.03f, 291.47f, 847.4f);
                    path3431Path.lineTo(291.47f, 839.76f);
                    path3431Path.lineTo(296.57f, 839.76f);
                    path3431Path.cubicTo(299.4f, 839.76f, 301.66f, 837.46f, 301.66f, 834.66f);
                    path3431Path.lineTo(301.66f, 827.02f);
                    path3431Path.cubicTo(301.66f, 824.19f, 299.4f, 821.92f, 296.57f, 821.92f);
                    path3431Path.close();
                    path3431Path.moveTo(296.57f, 834.66f);
                    path3431Path.lineTo(291.47f, 834.66f);
                    path3431Path.lineTo(291.47f, 827.02f);
                    path3431Path.lineTo(296.57f, 827.02f);
                    path3431Path.lineTo(296.57f, 834.66f);
                    path3431Path.close();
                    path3431Path.moveTo(255.81f, 862.69f);
                    path3431Path.lineTo(296.57f, 862.69f);
                    path3431Path.lineTo(296.57f, 867.78f);
                    path3431Path.lineTo(255.81f, 867.78f);
                    path3431Path.lineTo(255.81f, 862.69f);
                    path3431Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path3431Path, paint);
                }

                // path4657
                RectF path4657Rect = CacheForCanvas1.path4657Rect;
                path4657Rect.set(389.76f, 234.09f, 390.27f, 1918.3f);
                Path path4657Path = CacheForCanvas1.path4657Path;
                path4657Path.reset();
                path4657Path.moveTo(389.76f, 234.09f);
                path4657Path.lineTo(390.27f, 1918.3f);

                paint.reset();
                paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                paint.setStrokeWidth(1.07f);
                canvas.save();
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(strokeColor);
                canvas.drawPath(path4657Path, paint);
                canvas.restore();

                // path4657-3
                RectF path46573Rect = CacheForCanvas1.path46573Rect;
                path46573Rect.set(414.76f, 277.39f, 415.27f, 1961.6f);
                Path path46573Path = CacheForCanvas1.path46573Path;
                path46573Path.reset();
                path46573Path.moveTo(414.76f, 277.39f);
                path46573Path.lineTo(415.27f, 1961.6f);

                paint.reset();
                paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                paint.setStrokeWidth(1.07f);
                canvas.save();
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(strokeColor);
                canvas.drawPath(path46573Path, paint);
                canvas.restore();

                // path4657-6
                RectF path46576Rect = CacheForCanvas1.path46576Rect;
                path46576Rect.set(472.5f, 277.39f, 473f, 1961.6f);
                Path path46576Path = CacheForCanvas1.path46576Path;
                path46576Path.reset();
                path46576Path.moveTo(472.5f, 277.39f);
                path46576Path.lineTo(473f, 1961.6f);

                paint.reset();
                paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                paint.setStrokeWidth(1.07f);
                canvas.save();
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(strokeColor);
                canvas.drawPath(path46576Path, paint);
                canvas.restore();

                // rect4707
                canvas.saveLayerAlpha(null, 252, Canvas.ALL_SAVE_FLAG);
                {
                    RectF rect4707Rect = CacheForCanvas1.rect4707Rect;
                    rect4707Rect.set(213.47f, 2382.3f, 298.81f, 2467.63f);
                    Path rect4707Path = CacheForCanvas1.rect4707Path;
                    rect4707Path.reset();
                    rect4707Path.moveTo(rect4707Rect.left, rect4707Rect.top);
                    rect4707Path.lineTo(rect4707Rect.right, rect4707Rect.top);
                    rect4707Path.lineTo(rect4707Rect.right, rect4707Rect.bottom);
                    rect4707Path.lineTo(rect4707Rect.left, rect4707Rect.bottom);
                    rect4707Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor5);
                    canvas.drawPath(rect4707Path, paint);
                }
                canvas.restore();

                // flowRoot3269-7
                {
                    // path5076
                    RectF path5076Rect = CacheForCanvas1.path5076Rect;
                    path5076Rect.set(338.24f, 2400.17f, 372.55f, 2445.67f);
                    Path path5076Path = CacheForCanvas1.path5076Path;
                    path5076Path.reset();
                    path5076Path.moveTo(354.95f, 2428.14f);
                    path5076Path.lineTo(346.14f, 2428.14f);
                    path5076Path.lineTo(346.14f, 2445.67f);
                    path5076Path.lineTo(338.24f, 2445.67f);
                    path5076Path.lineTo(338.24f, 2400.17f);
                    path5076Path.lineTo(354.24f, 2400.17f);
                    path5076Path.cubicTo(359.49f, 2400.17f, 363.54f, 2401.35f, 366.39f, 2403.7f);
                    path5076Path.cubicTo(369.25f, 2406.06f, 370.67f, 2409.46f, 370.67f, 2413.92f);
                    path5076Path.cubicTo(370.67f, 2416.96f, 369.93f, 2419.52f, 368.45f, 2421.58f);
                    path5076Path.cubicTo(367f, 2423.62f, 364.95f, 2425.19f, 362.33f, 2426.3f);
                    path5076Path.lineTo(372.55f, 2445.27f);
                    path5076Path.lineTo(372.55f, 2445.67f);
                    path5076Path.lineTo(364.08f, 2445.67f);
                    path5076Path.lineTo(354.95f, 2428.14f);
                    path5076Path.close();
                    path5076Path.moveTo(346.14f, 2421.8f);
                    path5076Path.lineTo(354.27f, 2421.8f);
                    path5076Path.cubicTo(356.93f, 2421.8f, 359.02f, 2421.13f, 360.52f, 2419.8f);
                    path5076Path.cubicTo(362.02f, 2418.44f, 362.77f, 2416.6f, 362.77f, 2414.27f);
                    path5076Path.cubicTo(362.77f, 2411.83f, 362.07f, 2409.94f, 360.67f, 2408.61f);
                    path5076Path.cubicTo(359.3f, 2407.28f, 357.24f, 2406.59f, 354.49f, 2406.55f);
                    path5076Path.lineTo(346.14f, 2406.55f);
                    path5076Path.lineTo(346.14f, 2421.8f);
                    path5076Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5076Path, paint);

                    // path5078
                    RectF path5078Rect = CacheForCanvas1.path5078Rect;
                    path5078Rect.set(376.08f, 2411.24f, 407.52f, 2446.3f);
                    Path path5078Path = CacheForCanvas1.path5078Path;
                    path5078Path.reset();
                    path5078Path.moveTo(376.08f, 2428.45f);
                    path5078Path.cubicTo(376.08f, 2425.14f, 376.74f, 2422.16f, 378.05f, 2419.52f);
                    path5078Path.cubicTo(379.36f, 2416.85f, 381.2f, 2414.81f, 383.58f, 2413.39f);
                    path5078Path.cubicTo(385.95f, 2411.95f, 388.68f, 2411.24f, 391.77f, 2411.24f);
                    path5078Path.cubicTo(396.33f, 2411.24f, 400.03f, 2412.7f, 402.86f, 2415.64f);
                    path5078Path.cubicTo(405.71f, 2418.58f, 407.26f, 2422.48f, 407.49f, 2427.33f);
                    path5078Path.lineTo(407.52f, 2429.11f);
                    path5078Path.cubicTo(407.52f, 2432.44f, 406.87f, 2435.42f, 405.58f, 2438.05f);
                    path5078Path.cubicTo(404.31f, 2440.67f, 402.47f, 2442.7f, 400.08f, 2444.14f);
                    path5078Path.cubicTo(397.7f, 2445.58f, 394.95f, 2446.3f, 391.83f, 2446.3f);
                    path5078Path.cubicTo(387.06f, 2446.3f, 383.24f, 2444.71f, 380.36f, 2441.55f);
                    path5078Path.cubicTo(377.51f, 2438.36f, 376.08f, 2434.12f, 376.08f, 2428.83f);
                    path5078Path.lineTo(376.08f, 2428.45f);
                    path5078Path.close();
                    path5078Path.moveTo(383.67f, 2429.11f);
                    path5078Path.cubicTo(383.67f, 2432.59f, 384.39f, 2435.32f, 385.83f, 2437.3f);
                    path5078Path.cubicTo(387.27f, 2439.26f, 389.27f, 2440.24f, 391.83f, 2440.24f);
                    path5078Path.cubicTo(394.39f, 2440.24f, 396.38f, 2439.24f, 397.8f, 2437.24f);
                    path5078Path.cubicTo(399.24f, 2435.24f, 399.95f, 2432.31f, 399.95f, 2428.45f);
                    path5078Path.cubicTo(399.95f, 2425.04f, 399.21f, 2422.33f, 397.74f, 2420.33f);
                    path5078Path.cubicTo(396.28f, 2418.33f, 394.29f, 2417.33f, 391.77f, 2417.33f);
                    path5078Path.cubicTo(389.29f, 2417.33f, 387.32f, 2418.32f, 385.86f, 2420.3f);
                    path5078Path.cubicTo(384.4f, 2422.26f, 383.67f, 2425.19f, 383.67f, 2429.11f);
                    path5078Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5078Path, paint);

                    // path5080
                    RectF path5080Rect = CacheForCanvas1.path5080Rect;
                    path5080Rect.set(412.58f, 2411.24f, 444.02f, 2446.3f);
                    Path path5080Path = CacheForCanvas1.path5080Path;
                    path5080Path.reset();
                    path5080Path.moveTo(412.58f, 2428.45f);
                    path5080Path.cubicTo(412.58f, 2425.14f, 413.24f, 2422.16f, 414.55f, 2419.52f);
                    path5080Path.cubicTo(415.86f, 2416.85f, 417.7f, 2414.81f, 420.08f, 2413.39f);
                    path5080Path.cubicTo(422.45f, 2411.95f, 425.18f, 2411.24f, 428.27f, 2411.24f);
                    path5080Path.cubicTo(432.83f, 2411.24f, 436.53f, 2412.7f, 439.36f, 2415.64f);
                    path5080Path.cubicTo(442.21f, 2418.58f, 443.76f, 2422.48f, 443.99f, 2427.33f);
                    path5080Path.lineTo(444.02f, 2429.11f);
                    path5080Path.cubicTo(444.02f, 2432.44f, 443.37f, 2435.42f, 442.08f, 2438.05f);
                    path5080Path.cubicTo(440.81f, 2440.67f, 438.97f, 2442.7f, 436.58f, 2444.14f);
                    path5080Path.cubicTo(434.2f, 2445.58f, 431.45f, 2446.3f, 428.33f, 2446.3f);
                    path5080Path.cubicTo(423.56f, 2446.3f, 419.74f, 2444.71f, 416.86f, 2441.55f);
                    path5080Path.cubicTo(414.01f, 2438.36f, 412.58f, 2434.12f, 412.58f, 2428.83f);
                    path5080Path.lineTo(412.58f, 2428.45f);
                    path5080Path.close();
                    path5080Path.moveTo(420.17f, 2429.11f);
                    path5080Path.cubicTo(420.17f, 2432.59f, 420.89f, 2435.32f, 422.33f, 2437.3f);
                    path5080Path.cubicTo(423.77f, 2439.26f, 425.77f, 2440.24f, 428.33f, 2440.24f);
                    path5080Path.cubicTo(430.89f, 2440.24f, 432.88f, 2439.24f, 434.3f, 2437.24f);
                    path5080Path.cubicTo(435.74f, 2435.24f, 436.45f, 2432.31f, 436.45f, 2428.45f);
                    path5080Path.cubicTo(436.45f, 2425.04f, 435.71f, 2422.33f, 434.24f, 2420.33f);
                    path5080Path.cubicTo(432.78f, 2418.33f, 430.79f, 2417.33f, 428.27f, 2417.33f);
                    path5080Path.cubicTo(425.79f, 2417.33f, 423.82f, 2418.32f, 422.36f, 2420.3f);
                    path5080Path.cubicTo(420.9f, 2422.26f, 420.17f, 2425.19f, 420.17f, 2429.11f);
                    path5080Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5080Path, paint);

                    // path5082
                    RectF path5082Rect = CacheForCanvas1.path5082Rect;
                    path5082Rect.set(450.49f, 2411.24f, 498.39f, 2445.67f);
                    Path path5082Path = CacheForCanvas1.path5082Path;
                    path5082Path.reset();
                    path5082Path.moveTo(457.64f, 2411.86f);
                    path5082Path.lineTo(457.86f, 2415.39f);
                    path5082Path.cubicTo(460.24f, 2412.62f, 463.49f, 2411.24f, 467.61f, 2411.24f);
                    path5082Path.cubicTo(472.13f, 2411.24f, 475.22f, 2412.96f, 476.89f, 2416.42f);
                    path5082Path.cubicTo(479.35f, 2412.96f, 482.81f, 2411.24f, 487.27f, 2411.24f);
                    path5082Path.cubicTo(491f, 2411.24f, 493.77f, 2412.27f, 495.58f, 2414.33f);
                    path5082Path.cubicTo(497.41f, 2416.39f, 498.35f, 2419.43f, 498.39f, 2423.45f);
                    path5082Path.lineTo(498.39f, 2445.67f);
                    path5082Path.lineTo(490.8f, 2445.67f);
                    path5082Path.lineTo(490.8f, 2423.67f);
                    path5082Path.cubicTo(490.8f, 2421.53f, 490.33f, 2419.95f, 489.39f, 2418.95f);
                    path5082Path.cubicTo(488.45f, 2417.95f, 486.9f, 2417.45f, 484.74f, 2417.45f);
                    path5082Path.cubicTo(483.01f, 2417.45f, 481.59f, 2417.92f, 480.49f, 2418.86f);
                    path5082Path.cubicTo(479.4f, 2419.78f, 478.64f, 2420.99f, 478.2f, 2422.49f);
                    path5082Path.lineTo(478.24f, 2445.67f);
                    path5082Path.lineTo(470.64f, 2445.67f);
                    path5082Path.lineTo(470.64f, 2423.42f);
                    path5082Path.cubicTo(470.54f, 2419.44f, 468.51f, 2417.45f, 464.55f, 2417.45f);
                    path5082Path.cubicTo(461.51f, 2417.45f, 459.35f, 2418.69f, 458.08f, 2421.17f);
                    path5082Path.lineTo(458.08f, 2445.67f);
                    path5082Path.lineTo(450.49f, 2445.67f);
                    path5082Path.lineTo(450.49f, 2411.86f);
                    path5082Path.lineTo(457.64f, 2411.86f);
                    path5082Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5082Path, paint);

                    // path5084
                    RectF path5084Rect = CacheForCanvas1.path5084Rect;
                    path5084Rect.set(504.7f, 2411.24f, 532.67f, 2446.3f);
                    Path path5084Path = CacheForCanvas1.path5084Path;
                    path5084Path.reset();
                    path5084Path.moveTo(525.2f, 2436.49f);
                    path5084Path.cubicTo(525.2f, 2435.13f, 524.64f, 2434.1f, 523.52f, 2433.39f);
                    path5084Path.cubicTo(522.41f, 2432.68f, 520.57f, 2432.06f, 517.99f, 2431.52f);
                    path5084Path.cubicTo(515.4f, 2430.98f, 513.25f, 2430.29f, 511.52f, 2429.45f);
                    path5084Path.cubicTo(507.72f, 2427.62f, 505.83f, 2424.96f, 505.83f, 2421.49f);
                    path5084Path.cubicTo(505.83f, 2418.57f, 507.06f, 2416.13f, 509.52f, 2414.17f);
                    path5084Path.cubicTo(511.97f, 2412.21f, 515.1f, 2411.24f, 518.89f, 2411.24f);
                    path5084Path.cubicTo(522.93f, 2411.24f, 526.19f, 2412.24f, 528.67f, 2414.24f);
                    path5084Path.cubicTo(531.17f, 2416.24f, 532.42f, 2418.83f, 532.42f, 2422.02f);
                    path5084Path.lineTo(524.83f, 2422.02f);
                    path5084Path.cubicTo(524.83f, 2420.56f, 524.29f, 2419.35f, 523.2f, 2418.39f);
                    path5084Path.cubicTo(522.12f, 2417.41f, 520.68f, 2416.92f, 518.89f, 2416.92f);
                    path5084Path.cubicTo(517.22f, 2416.92f, 515.86f, 2417.31f, 514.8f, 2418.08f);
                    path5084Path.cubicTo(513.76f, 2418.85f, 513.24f, 2419.88f, 513.24f, 2421.17f);
                    path5084Path.cubicTo(513.24f, 2422.34f, 513.72f, 2423.25f, 514.7f, 2423.89f);
                    path5084Path.cubicTo(515.68f, 2424.54f, 517.66f, 2425.19f, 520.64f, 2425.86f);
                    path5084Path.cubicTo(523.62f, 2426.51f, 525.95f, 2427.29f, 527.64f, 2428.2f);
                    path5084Path.cubicTo(529.35f, 2429.1f, 530.61f, 2430.18f, 531.42f, 2431.45f);
                    path5084Path.cubicTo(532.26f, 2432.73f, 532.67f, 2434.27f, 532.67f, 2436.08f);
                    path5084Path.cubicTo(532.67f, 2439.12f, 531.41f, 2441.59f, 528.89f, 2443.49f);
                    path5084Path.cubicTo(526.37f, 2445.36f, 523.07f, 2446.3f, 518.99f, 2446.3f);
                    path5084Path.cubicTo(516.21f, 2446.3f, 513.75f, 2445.8f, 511.58f, 2444.8f);
                    path5084Path.cubicTo(509.41f, 2443.8f, 507.72f, 2442.42f, 506.52f, 2440.67f);
                    path5084Path.cubicTo(505.31f, 2438.92f, 504.7f, 2437.04f, 504.7f, 2435.02f);
                    path5084Path.lineTo(512.08f, 2435.02f);
                    path5084Path.cubicTo(512.18f, 2436.81f, 512.86f, 2438.19f, 514.11f, 2439.17f);
                    path5084Path.cubicTo(515.36f, 2440.13f, 517.02f, 2440.61f, 519.08f, 2440.61f);
                    path5084Path.cubicTo(521.08f, 2440.61f, 522.6f, 2440.24f, 523.64f, 2439.49f);
                    path5084Path.cubicTo(524.68f, 2438.71f, 525.2f, 2437.71f, 525.2f, 2436.49f);
                    path5084Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5084Path, paint);
                }

                // rect4707-6
                canvas.saveLayerAlpha(null, 252, Canvas.ALL_SAVE_FLAG);
                {
                    RectF rect47076Rect = CacheForCanvas1.rect47076Rect;
                    rect47076Rect.set(213.47f, 2488.96f, 298.81f, 2574.3f);
                    Path rect47076Path = CacheForCanvas1.rect47076Path;
                    rect47076Path.reset();
                    rect47076Path.moveTo(rect47076Rect.left, rect47076Rect.top);
                    rect47076Path.lineTo(rect47076Rect.right, rect47076Rect.top);
                    rect47076Path.lineTo(rect47076Rect.right, rect47076Rect.bottom);
                    rect47076Path.lineTo(rect47076Rect.left, rect47076Rect.bottom);
                    rect47076Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor3);
                    canvas.drawPath(rect47076Path, paint);
                }
                canvas.restore();

                // flowRoot3269-7-7
                {
                    // path5059
                    RectF path5059Rect = CacheForCanvas1.path5059Rect;
                    path5059Rect.set(338.24f, 2502.64f, 372.24f, 2549.39f);
                    Path path5059Path = CacheForCanvas1.path5059Path;
                    path5059Path.reset();
                    path5059Path.moveTo(364.33f, 2537.05f);
                    path5059Path.cubicTo(364.33f, 2535.05f, 363.62f, 2533.51f, 362.2f, 2532.42f);
                    path5059Path.cubicTo(360.81f, 2531.34f, 358.28f, 2530.25f, 354.61f, 2529.14f);
                    path5059Path.cubicTo(350.94f, 2528.04f, 348.03f, 2526.81f, 345.86f, 2525.45f);
                    path5059Path.cubicTo(341.71f, 2522.85f, 339.64f, 2519.45f, 339.64f, 2515.27f);
                    path5059Path.cubicTo(339.64f, 2511.6f, 341.13f, 2508.58f, 344.11f, 2506.2f);
                    path5059Path.cubicTo(347.11f, 2503.83f, 351f, 2502.64f, 355.77f, 2502.64f);
                    path5059Path.cubicTo(358.93f, 2502.64f, 361.76f, 2503.22f, 364.24f, 2504.39f);
                    path5059Path.cubicTo(366.71f, 2505.56f, 368.66f, 2507.22f, 370.08f, 2509.39f);
                    path5059Path.cubicTo(371.5f, 2511.54f, 372.2f, 2513.92f, 372.2f, 2516.55f);
                    path5059Path.lineTo(364.33f, 2516.55f);
                    path5059Path.cubicTo(364.33f, 2514.17f, 363.58f, 2512.32f, 362.08f, 2510.99f);
                    path5059Path.cubicTo(360.6f, 2509.63f, 358.47f, 2508.95f, 355.7f, 2508.95f);
                    path5059Path.cubicTo(353.12f, 2508.95f, 351.11f, 2509.51f, 349.67f, 2510.61f);
                    path5059Path.cubicTo(348.26f, 2511.71f, 347.55f, 2513.26f, 347.55f, 2515.24f);
                    path5059Path.cubicTo(347.55f, 2516.9f, 348.32f, 2518.3f, 349.86f, 2519.42f);
                    path5059Path.cubicTo(351.4f, 2520.53f, 353.94f, 2521.61f, 357.49f, 2522.67f);
                    path5059Path.cubicTo(361.03f, 2523.71f, 363.87f, 2524.91f, 366.02f, 2526.27f);
                    path5059Path.cubicTo(368.16f, 2527.6f, 369.74f, 2529.14f, 370.74f, 2530.89f);
                    path5059Path.cubicTo(371.74f, 2532.62f, 372.24f, 2534.65f, 372.24f, 2536.99f);
                    path5059Path.cubicTo(372.24f, 2540.78f, 370.78f, 2543.8f, 367.86f, 2546.05f);
                    path5059Path.cubicTo(364.96f, 2548.28f, 361.03f, 2549.39f, 356.05f, 2549.39f);
                    path5059Path.cubicTo(352.76f, 2549.39f, 349.72f, 2548.79f, 346.95f, 2547.58f);
                    path5059Path.cubicTo(344.2f, 2546.35f, 342.06f, 2544.66f, 340.52f, 2542.52f);
                    path5059Path.cubicTo(339f, 2540.37f, 338.24f, 2537.87f, 338.24f, 2535.02f);
                    path5059Path.lineTo(346.14f, 2535.02f);
                    path5059Path.cubicTo(346.14f, 2537.6f, 347f, 2539.6f, 348.7f, 2541.02f);
                    path5059Path.cubicTo(350.41f, 2542.43f, 352.86f, 2543.14f, 356.05f, 2543.14f);
                    path5059Path.cubicTo(358.8f, 2543.14f, 360.86f, 2542.59f, 362.24f, 2541.49f);
                    path5059Path.cubicTo(363.63f, 2540.36f, 364.33f, 2538.88f, 364.33f, 2537.05f);
                    path5059Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5059Path, paint);

                    // path5061
                    RectF path5061Rect = CacheForCanvas1.path5061Rect;
                    path5061Rect.set(378.42f, 2514.33f, 408.05f, 2561.77f);
                    Path path5061Path = CacheForCanvas1.path5061Path;
                    path5061Path.reset();
                    path5061Path.moveTo(408.05f, 2532.2f);
                    path5061Path.cubicTo(408.05f, 2537.43f, 406.86f, 2541.61f, 404.49f, 2544.74f);
                    path5061Path.cubicTo(402.11f, 2547.84f, 398.92f, 2549.39f, 394.92f, 2549.39f);
                    path5061Path.cubicTo(391.21f, 2549.39f, 388.25f, 2548.17f, 386.02f, 2545.74f);
                    path5061Path.lineTo(386.02f, 2561.77f);
                    path5061Path.lineTo(378.42f, 2561.77f);
                    path5061Path.lineTo(378.42f, 2514.95f);
                    path5061Path.lineTo(385.42f, 2514.95f);
                    path5061Path.lineTo(385.74f, 2518.39f);
                    path5061Path.cubicTo(387.96f, 2515.68f, 391f, 2514.33f, 394.83f, 2514.33f);
                    path5061Path.cubicTo(398.95f, 2514.33f, 402.18f, 2515.87f, 404.52f, 2518.95f);
                    path5061Path.cubicTo(406.87f, 2522.02f, 408.05f, 2526.28f, 408.05f, 2531.74f);
                    path5061Path.lineTo(408.05f, 2532.2f);
                    path5061Path.close();
                    path5061Path.moveTo(400.49f, 2531.55f);
                    path5061Path.cubicTo(400.49f, 2528.17f, 399.81f, 2525.5f, 398.45f, 2523.52f);
                    path5061Path.cubicTo(397.12f, 2521.54f, 395.2f, 2520.55f, 392.7f, 2520.55f);
                    path5061Path.cubicTo(389.6f, 2520.55f, 387.37f, 2521.83f, 386.02f, 2524.39f);
                    path5061Path.lineTo(386.02f, 2539.39f);
                    path5061Path.cubicTo(387.39f, 2542.02f, 389.64f, 2543.33f, 392.77f, 2543.33f);
                    path5061Path.cubicTo(395.18f, 2543.33f, 397.07f, 2542.36f, 398.42f, 2540.42f);
                    path5061Path.cubicTo(399.8f, 2538.46f, 400.49f, 2535.51f, 400.49f, 2531.55f);
                    path5061Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5061Path, paint);

                    // path5063
                    RectF path5063Rect = CacheForCanvas1.path5063Rect;
                    path5063Rect.set(413.02f, 2514.33f, 444.45f, 2549.39f);
                    Path path5063Path = CacheForCanvas1.path5063Path;
                    path5063Path.reset();
                    path5063Path.moveTo(413.02f, 2531.55f);
                    path5063Path.cubicTo(413.02f, 2528.24f, 413.67f, 2525.26f, 414.99f, 2522.61f);
                    path5063Path.cubicTo(416.3f, 2519.94f, 418.14f, 2517.9f, 420.52f, 2516.49f);
                    path5063Path.cubicTo(422.89f, 2515.05f, 425.62f, 2514.33f, 428.7f, 2514.33f);
                    path5063Path.cubicTo(433.27f, 2514.33f, 436.96f, 2515.8f, 439.8f, 2518.74f);
                    path5063Path.cubicTo(442.65f, 2521.67f, 444.19f, 2525.57f, 444.42f, 2530.42f);
                    path5063Path.lineTo(444.45f, 2532.2f);
                    path5063Path.cubicTo(444.45f, 2535.54f, 443.81f, 2538.52f, 442.52f, 2541.14f);
                    path5063Path.cubicTo(441.25f, 2543.77f, 439.41f, 2545.8f, 437.02f, 2547.24f);
                    path5063Path.cubicTo(434.64f, 2548.67f, 431.89f, 2549.39f, 428.77f, 2549.39f);
                    path5063Path.cubicTo(424f, 2549.39f, 420.17f, 2547.81f, 417.3f, 2544.64f);
                    path5063Path.cubicTo(414.44f, 2541.45f, 413.02f, 2537.21f, 413.02f, 2531.92f);
                    path5063Path.lineTo(413.02f, 2531.55f);
                    path5063Path.close();
                    path5063Path.moveTo(420.61f, 2532.2f);
                    path5063Path.cubicTo(420.61f, 2535.68f, 421.33f, 2538.41f, 422.77f, 2540.39f);
                    path5063Path.cubicTo(424.2f, 2542.35f, 426.2f, 2543.33f, 428.77f, 2543.33f);
                    path5063Path.cubicTo(431.33f, 2543.33f, 433.32f, 2542.33f, 434.74f, 2540.33f);
                    path5063Path.cubicTo(436.17f, 2538.33f, 436.89f, 2535.4f, 436.89f, 2531.55f);
                    path5063Path.cubicTo(436.89f, 2528.13f, 436.15f, 2525.42f, 434.67f, 2523.42f);
                    path5063Path.cubicTo(433.21f, 2521.42f, 431.22f, 2520.42f, 428.7f, 2520.42f);
                    path5063Path.cubicTo(426.22f, 2520.42f, 424.26f, 2521.41f, 422.8f, 2523.39f);
                    path5063Path.cubicTo(421.34f, 2525.35f, 420.61f, 2528.29f, 420.61f, 2532.2f);
                    path5063Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5063Path, paint);

                    // path5065
                    RectF path5065Rect = CacheForCanvas1.path5065Rect;
                    path5065Rect.set(450.83f, 2514.33f, 478.8f, 2548.77f);
                    Path path5065Path = CacheForCanvas1.path5065Path;
                    path5065Path.reset();
                    path5065Path.moveTo(457.99f, 2514.95f);
                    path5065Path.lineTo(458.2f, 2518.86f);
                    path5065Path.cubicTo(460.7f, 2515.84f, 463.99f, 2514.33f, 468.05f, 2514.33f);
                    path5065Path.cubicTo(475.09f, 2514.33f, 478.67f, 2518.36f, 478.8f, 2526.42f);
                    path5065Path.lineTo(478.8f, 2548.77f);
                    path5065Path.lineTo(471.2f, 2548.77f);
                    path5065Path.lineTo(471.2f, 2526.86f);
                    path5065Path.cubicTo(471.2f, 2524.71f, 470.74f, 2523.13f, 469.8f, 2522.11f);
                    path5065Path.cubicTo(468.88f, 2521.07f, 467.37f, 2520.55f, 465.27f, 2520.55f);
                    path5065Path.cubicTo(462.2f, 2520.55f, 459.92f, 2521.93f, 458.42f, 2524.7f);
                    path5065Path.lineTo(458.42f, 2548.77f);
                    path5065Path.lineTo(450.83f, 2548.77f);
                    path5065Path.lineTo(450.83f, 2514.95f);
                    path5065Path.lineTo(457.99f, 2514.95f);
                    path5065Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5065Path, paint);

                    // path5067
                    RectF path5067Rect = CacheForCanvas1.path5067Rect;
                    path5067Rect.set(485.02f, 2514.33f, 512.99f, 2549.39f);
                    Path path5067Path = CacheForCanvas1.path5067Path;
                    path5067Path.reset();
                    path5067Path.moveTo(505.52f, 2539.58f);
                    path5067Path.cubicTo(505.52f, 2538.22f, 504.95f, 2537.19f, 503.83f, 2536.49f);
                    path5067Path.cubicTo(502.72f, 2535.78f, 500.88f, 2535.15f, 498.3f, 2534.61f);
                    path5067Path.cubicTo(495.71f, 2534.07f, 493.56f, 2533.38f, 491.83f, 2532.55f);
                    path5067Path.cubicTo(488.04f, 2530.71f, 486.14f, 2528.06f, 486.14f, 2524.58f);
                    path5067Path.cubicTo(486.14f, 2521.66f, 487.37f, 2519.22f, 489.83f, 2517.27f);
                    path5067Path.cubicTo(492.29f, 2515.31f, 495.41f, 2514.33f, 499.2f, 2514.33f);
                    path5067Path.cubicTo(503.25f, 2514.33f, 506.51f, 2515.33f, 508.99f, 2517.33f);
                    path5067Path.cubicTo(511.49f, 2519.33f, 512.74f, 2521.92f, 512.74f, 2525.11f);
                    path5067Path.lineTo(505.14f, 2525.11f);
                    path5067Path.cubicTo(505.14f, 2523.65f, 504.6f, 2522.44f, 503.52f, 2521.49f);
                    path5067Path.cubicTo(502.43f, 2520.51f, 501f, 2520.02f, 499.2f, 2520.02f);
                    path5067Path.cubicTo(497.54f, 2520.02f, 496.17f, 2520.4f, 495.11f, 2521.17f);
                    path5067Path.cubicTo(494.07f, 2521.94f, 493.55f, 2522.97f, 493.55f, 2524.27f);
                    path5067Path.cubicTo(493.55f, 2525.43f, 494.04f, 2526.34f, 495.02f, 2526.99f);
                    path5067Path.cubicTo(496f, 2527.63f, 497.97f, 2528.29f, 500.95f, 2528.95f);
                    path5067Path.cubicTo(503.93f, 2529.6f, 506.27f, 2530.38f, 507.95f, 2531.3f);
                    path5067Path.cubicTo(509.66f, 2532.19f, 510.92f, 2533.28f, 511.74f, 2534.55f);
                    path5067Path.cubicTo(512.57f, 2535.82f, 512.99f, 2537.36f, 512.99f, 2539.17f);
                    path5067Path.cubicTo(512.99f, 2542.21f, 511.72f, 2544.68f, 509.2f, 2546.58f);
                    path5067Path.cubicTo(506.68f, 2548.45f, 503.38f, 2549.39f, 499.3f, 2549.39f);
                    path5067Path.cubicTo(496.53f, 2549.39f, 494.06f, 2548.89f, 491.89f, 2547.89f);
                    path5067Path.cubicTo(489.72f, 2546.89f, 488.04f, 2545.52f, 486.83f, 2543.77f);
                    path5067Path.cubicTo(485.62f, 2542.02f, 485.02f, 2540.13f, 485.02f, 2538.11f);
                    path5067Path.lineTo(492.39f, 2538.11f);
                    path5067Path.cubicTo(492.5f, 2539.9f, 493.17f, 2541.29f, 494.42f, 2542.27f);
                    path5067Path.cubicTo(495.67f, 2543.22f, 497.33f, 2543.7f, 499.39f, 2543.7f);
                    path5067Path.cubicTo(501.39f, 2543.7f, 502.91f, 2543.33f, 503.95f, 2542.58f);
                    path5067Path.cubicTo(505f, 2541.81f, 505.52f, 2540.81f, 505.52f, 2539.58f);
                    path5067Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5067Path, paint);

                    // path5069
                    RectF path5069Rect = CacheForCanvas1.path5069Rect;
                    path5069Rect.set(518.14f, 2514.33f, 549.58f, 2549.39f);
                    Path path5069Path = CacheForCanvas1.path5069Path;
                    path5069Path.reset();
                    path5069Path.moveTo(518.14f, 2531.55f);
                    path5069Path.cubicTo(518.14f, 2528.24f, 518.8f, 2525.26f, 520.11f, 2522.61f);
                    path5069Path.cubicTo(521.42f, 2519.94f, 523.27f, 2517.9f, 525.64f, 2516.49f);
                    path5069Path.cubicTo(528.02f, 2515.05f, 530.75f, 2514.33f, 533.83f, 2514.33f);
                    path5069Path.cubicTo(538.39f, 2514.33f, 542.09f, 2515.8f, 544.92f, 2518.74f);
                    path5069Path.cubicTo(547.78f, 2521.67f, 549.32f, 2525.57f, 549.55f, 2530.42f);
                    path5069Path.lineTo(549.58f, 2532.2f);
                    path5069Path.cubicTo(549.58f, 2535.54f, 548.93f, 2538.52f, 547.64f, 2541.14f);
                    path5069Path.cubicTo(546.37f, 2543.77f, 544.54f, 2545.8f, 542.14f, 2547.24f);
                    path5069Path.cubicTo(539.77f, 2548.67f, 537.02f, 2549.39f, 533.89f, 2549.39f);
                    path5069Path.cubicTo(529.12f, 2549.39f, 525.3f, 2547.81f, 522.42f, 2544.64f);
                    path5069Path.cubicTo(519.57f, 2541.45f, 518.14f, 2537.21f, 518.14f, 2531.92f);
                    path5069Path.lineTo(518.14f, 2531.55f);
                    path5069Path.close();
                    path5069Path.moveTo(525.74f, 2532.2f);
                    path5069Path.cubicTo(525.74f, 2535.68f, 526.45f, 2538.41f, 527.89f, 2540.39f);
                    path5069Path.cubicTo(529.33f, 2542.35f, 531.33f, 2543.33f, 533.89f, 2543.33f);
                    path5069Path.cubicTo(536.45f, 2543.33f, 538.44f, 2542.33f, 539.86f, 2540.33f);
                    path5069Path.cubicTo(541.3f, 2538.33f, 542.02f, 2535.4f, 542.02f, 2531.55f);
                    path5069Path.cubicTo(542.02f, 2528.13f, 541.28f, 2525.42f, 539.8f, 2523.42f);
                    path5069Path.cubicTo(538.34f, 2521.42f, 536.35f, 2520.42f, 533.83f, 2520.42f);
                    path5069Path.cubicTo(531.35f, 2520.42f, 529.38f, 2521.41f, 527.92f, 2523.39f);
                    path5069Path.cubicTo(526.46f, 2525.35f, 525.74f, 2528.29f, 525.74f, 2532.2f);
                    path5069Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5069Path, paint);

                    // path5071
                    RectF path5071Rect = CacheForCanvas1.path5071Rect;
                    path5071Rect.set(556.05f, 2514.33f, 573.8f, 2548.77f);
                    Path path5071Path = CacheForCanvas1.path5071Path;
                    path5071Path.reset();
                    path5071Path.moveTo(573.77f, 2521.89f);
                    path5071Path.cubicTo(572.77f, 2521.72f, 571.74f, 2521.64f, 570.67f, 2521.64f);
                    path5071Path.cubicTo(567.19f, 2521.64f, 564.85f, 2522.97f, 563.64f, 2525.64f);
                    path5071Path.lineTo(563.64f, 2548.77f);
                    path5071Path.lineTo(556.05f, 2548.77f);
                    path5071Path.lineTo(556.05f, 2514.95f);
                    path5071Path.lineTo(563.3f, 2514.95f);
                    path5071Path.lineTo(563.49f, 2518.74f);
                    path5071Path.cubicTo(565.32f, 2515.8f, 567.86f, 2514.33f, 571.11f, 2514.33f);
                    path5071Path.cubicTo(572.19f, 2514.33f, 573.09f, 2514.47f, 573.8f, 2514.77f);
                    path5071Path.lineTo(573.77f, 2521.89f);
                    path5071Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5071Path, paint);

                    // path5073
                    RectF path5073Rect = CacheForCanvas1.path5073Rect;
                    path5073Rect.set(577.02f, 2514.33f, 604.99f, 2549.39f);
                    Path path5073Path = CacheForCanvas1.path5073Path;
                    path5073Path.reset();
                    path5073Path.moveTo(597.52f, 2539.58f);
                    path5073Path.cubicTo(597.52f, 2538.22f, 596.95f, 2537.19f, 595.83f, 2536.49f);
                    path5073Path.cubicTo(594.72f, 2535.78f, 592.88f, 2535.15f, 590.3f, 2534.61f);
                    path5073Path.cubicTo(587.71f, 2534.07f, 585.56f, 2533.38f, 583.83f, 2532.55f);
                    path5073Path.cubicTo(580.04f, 2530.71f, 578.14f, 2528.06f, 578.14f, 2524.58f);
                    path5073Path.cubicTo(578.14f, 2521.66f, 579.37f, 2519.22f, 581.83f, 2517.27f);
                    path5073Path.cubicTo(584.29f, 2515.31f, 587.41f, 2514.33f, 591.2f, 2514.33f);
                    path5073Path.cubicTo(595.25f, 2514.33f, 598.51f, 2515.33f, 600.99f, 2517.33f);
                    path5073Path.cubicTo(603.49f, 2519.33f, 604.74f, 2521.92f, 604.74f, 2525.11f);
                    path5073Path.lineTo(597.14f, 2525.11f);
                    path5073Path.cubicTo(597.14f, 2523.65f, 596.6f, 2522.44f, 595.52f, 2521.49f);
                    path5073Path.cubicTo(594.43f, 2520.51f, 593f, 2520.02f, 591.2f, 2520.02f);
                    path5073Path.cubicTo(589.54f, 2520.02f, 588.17f, 2520.4f, 587.11f, 2521.17f);
                    path5073Path.cubicTo(586.07f, 2521.94f, 585.55f, 2522.97f, 585.55f, 2524.27f);
                    path5073Path.cubicTo(585.55f, 2525.43f, 586.04f, 2526.34f, 587.02f, 2526.99f);
                    path5073Path.cubicTo(588f, 2527.63f, 589.97f, 2528.29f, 592.95f, 2528.95f);
                    path5073Path.cubicTo(595.93f, 2529.6f, 598.27f, 2530.38f, 599.95f, 2531.3f);
                    path5073Path.cubicTo(601.66f, 2532.19f, 602.92f, 2533.28f, 603.74f, 2534.55f);
                    path5073Path.cubicTo(604.57f, 2535.82f, 604.99f, 2537.36f, 604.99f, 2539.17f);
                    path5073Path.cubicTo(604.99f, 2542.21f, 603.72f, 2544.68f, 601.2f, 2546.58f);
                    path5073Path.cubicTo(598.68f, 2548.45f, 595.38f, 2549.39f, 591.3f, 2549.39f);
                    path5073Path.cubicTo(588.53f, 2549.39f, 586.06f, 2548.89f, 583.89f, 2547.89f);
                    path5073Path.cubicTo(581.72f, 2546.89f, 580.04f, 2545.52f, 578.83f, 2543.77f);
                    path5073Path.cubicTo(577.62f, 2542.02f, 577.02f, 2540.13f, 577.02f, 2538.11f);
                    path5073Path.lineTo(584.39f, 2538.11f);
                    path5073Path.cubicTo(584.5f, 2539.9f, 585.17f, 2541.29f, 586.42f, 2542.27f);
                    path5073Path.cubicTo(587.67f, 2543.22f, 589.33f, 2543.7f, 591.39f, 2543.7f);
                    path5073Path.cubicTo(593.39f, 2543.7f, 594.91f, 2543.33f, 595.95f, 2542.58f);
                    path5073Path.cubicTo(597f, 2541.81f, 597.52f, 2540.81f, 597.52f, 2539.58f);
                    path5073Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5073Path, paint);
                }

                // g4798
                {
                    // path4788
                    RectF path4788Rect = CacheForCanvas1.path4788Rect;
                    path4788Rect.set(300.55f, 1273.32f, 366.15f, 1338.92f);

                    // path4790
                    RectF path4790Rect = CacheForCanvas1.path4790Rect;
                    path4790Rect.set(306f, 1278.77f, 360.65f, 1333.42f);
                    Path path4790Path = CacheForCanvas1.path4790Path;
                    path4790Path.reset();
                    path4790Path.moveTo(333.33f, 1278.77f);
                    path4790Path.cubicTo(318.24f, 1278.77f, 306f, 1291.01f, 306f, 1306.09f);
                    path4790Path.cubicTo(306f, 1321.18f, 318.24f, 1333.42f, 333.33f, 1333.42f);
                    path4790Path.cubicTo(348.41f, 1333.42f, 360.65f, 1321.18f, 360.65f, 1306.09f);
                    path4790Path.cubicTo(360.65f, 1291.01f, 348.41f, 1278.77f, 333.33f, 1278.77f);
                    path4790Path.close();
                    path4790Path.moveTo(327.86f, 1319.76f);
                    path4790Path.lineTo(314.2f, 1306.09f);
                    path4790Path.lineTo(318.05f, 1302.24f);
                    path4790Path.lineTo(327.86f, 1312.02f);
                    path4790Path.lineTo(348.6f, 1291.28f);
                    path4790Path.lineTo(352.46f, 1295.16f);
                    path4790Path.lineTo(327.86f, 1319.76f);
                    path4790Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path4790Path, paint);
                }

                // g4798-6
                {
                    // path4788-0
                    RectF path47880Rect = CacheForCanvas1.path47880Rect;
                    path47880Rect.set(326.55f, 2613.38f, 398.25f, 2685.08f);

                    // path4790-6
                    RectF path47906Rect = CacheForCanvas1.path47906Rect;
                    path47906Rect.set(332.53f, 2619.36f, 392.29f, 2679.13f);
                    Path path47906Path = CacheForCanvas1.path47906Path;
                    path47906Path.reset();
                    path47906Path.moveTo(362.41f, 2619.36f);
                    path47906Path.cubicTo(345.91f, 2619.36f, 332.53f, 2632.75f, 332.53f, 2649.25f);
                    path47906Path.cubicTo(332.53f, 2665.74f, 345.91f, 2679.13f, 362.41f, 2679.13f);
                    path47906Path.cubicTo(378.91f, 2679.13f, 392.29f, 2665.74f, 392.29f, 2649.25f);
                    path47906Path.cubicTo(392.29f, 2632.75f, 378.91f, 2619.36f, 362.41f, 2619.36f);
                    path47906Path.close();
                    path47906Path.moveTo(356.43f, 2664.19f);
                    path47906Path.lineTo(341.49f, 2649.25f);
                    path47906Path.lineTo(345.7f, 2645.03f);
                    path47906Path.lineTo(356.43f, 2655.73f);
                    path47906Path.lineTo(379.11f, 2633.05f);
                    path47906Path.lineTo(383.33f, 2637.29f);
                    path47906Path.lineTo(356.43f, 2664.19f);
                    path47906Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path47906Path, paint);
                }

                // g3427-2
                {
                    // path3413-6
                    RectF path34136Rect = CacheForCanvas1.path34136Rect;
                    path34136Rect.set(329.22f, 2717.68f, 403.28f, 2791.73f);

                    // path3415-1
                    RectF path34151Rect = CacheForCanvas1.path34151Rect;
                    path34151Rect.set(335.4f, 2723.82f, 397.1f, 2785.53f);
                    Path path34151Path = CacheForCanvas1.path34151Path;
                    path34151Path.reset();
                    path34151Path.moveTo(395.28f, 2753.38f);
                    path34151Path.lineTo(367.51f, 2725.61f);
                    path34151Path.cubicTo(366.4f, 2724.5f, 364.86f, 2723.82f, 363.16f, 2723.82f);
                    path34151Path.lineTo(341.57f, 2723.82f);
                    path34151Path.cubicTo(338.17f, 2723.82f, 335.4f, 2726.6f, 335.4f, 2729.99f);
                    path34151Path.lineTo(335.4f, 2751.59f);
                    path34151Path.cubicTo(335.4f, 2753.29f, 336.08f, 2754.83f, 337.22f, 2755.97f);
                    path34151Path.lineTo(364.98f, 2783.74f);
                    path34151Path.cubicTo(366.09f, 2784.85f, 367.64f, 2785.53f, 369.33f, 2785.53f);
                    path34151Path.cubicTo(371.03f, 2785.53f, 372.57f, 2784.85f, 373.68f, 2783.71f);
                    path34151Path.lineTo(395.28f, 2762.11f);
                    path34151Path.cubicTo(396.42f, 2761f, 397.1f, 2759.46f, 397.1f, 2757.76f);
                    path34151Path.cubicTo(397.1f, 2756.06f, 396.39f, 2754.49f, 395.28f, 2753.38f);
                    path34151Path.close();
                    path34151Path.moveTo(346.19f, 2739.25f);
                    path34151Path.cubicTo(343.63f, 2739.25f, 341.57f, 2737.18f, 341.57f, 2734.62f);
                    path34151Path.cubicTo(341.57f, 2732.06f, 343.63f, 2729.99f, 346.19f, 2729.99f);
                    path34151Path.cubicTo(348.76f, 2729.99f, 350.82f, 2732.06f, 350.82f, 2734.62f);
                    path34151Path.cubicTo(350.82f, 2737.18f, 348.76f, 2739.25f, 346.19f, 2739.25f);
                    path34151Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path34151Path, paint);
                }

                // flowRoot3269-7-7-8
                {
                    // path5034
                    RectF path5034Rect = CacheForCanvas1.path5034Rect;
                    path5034Rect.set(412.96f, 2624.42f, 447.27f, 2669.92f);
                    Path path5034Path = CacheForCanvas1.path5034Path;
                    path5034Path.reset();
                    path5034Path.moveTo(429.67f, 2652.38f);
                    path5034Path.lineTo(420.86f, 2652.38f);
                    path5034Path.lineTo(420.86f, 2669.92f);
                    path5034Path.lineTo(412.96f, 2669.92f);
                    path5034Path.lineTo(412.96f, 2624.42f);
                    path5034Path.lineTo(428.96f, 2624.42f);
                    path5034Path.cubicTo(434.21f, 2624.42f, 438.26f, 2625.59f, 441.11f, 2627.95f);
                    path5034Path.cubicTo(443.97f, 2630.3f, 445.39f, 2633.71f, 445.39f, 2638.17f);
                    path5034Path.cubicTo(445.39f, 2641.21f, 444.65f, 2643.76f, 443.17f, 2645.82f);
                    path5034Path.cubicTo(441.72f, 2647.86f, 439.67f, 2649.44f, 437.05f, 2650.54f);
                    path5034Path.lineTo(447.27f, 2669.51f);
                    path5034Path.lineTo(447.27f, 2669.92f);
                    path5034Path.lineTo(438.8f, 2669.92f);
                    path5034Path.lineTo(429.67f, 2652.38f);
                    path5034Path.close();
                    path5034Path.moveTo(420.86f, 2646.04f);
                    path5034Path.lineTo(428.99f, 2646.04f);
                    path5034Path.cubicTo(431.65f, 2646.04f, 433.74f, 2645.37f, 435.24f, 2644.04f);
                    path5034Path.cubicTo(436.74f, 2642.69f, 437.49f, 2640.84f, 437.49f, 2638.51f);
                    path5034Path.cubicTo(437.49f, 2636.07f, 436.79f, 2634.19f, 435.39f, 2632.85f);
                    path5034Path.cubicTo(434.02f, 2631.52f, 431.96f, 2630.83f, 429.21f, 2630.79f);
                    path5034Path.lineTo(420.86f, 2630.79f);
                    path5034Path.lineTo(420.86f, 2646.04f);
                    path5034Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5034Path, paint);

                    // path5036
                    RectF path5036Rect = CacheForCanvas1.path5036Rect;
                    path5036Rect.set(450.92f, 2635.48f, 480.67f, 2670.54f);
                    Path path5036Path = CacheForCanvas1.path5036Path;
                    path5036Path.reset();
                    path5036Path.moveTo(467.11f, 2670.54f);
                    path5036Path.cubicTo(462.3f, 2670.54f, 458.39f, 2669.03f, 455.39f, 2666.01f);
                    path5036Path.cubicTo(452.41f, 2662.97f, 450.92f, 2658.93f, 450.92f, 2653.88f);
                    path5036Path.lineTo(450.92f, 2652.95f);
                    path5036Path.cubicTo(450.92f, 2649.57f, 451.57f, 2646.56f, 452.86f, 2643.92f);
                    path5036Path.cubicTo(454.17f, 2641.25f, 456.01f, 2639.18f, 458.36f, 2637.7f);
                    path5036Path.cubicTo(460.72f, 2636.22f, 463.34f, 2635.48f, 466.24f, 2635.48f);
                    path5036Path.cubicTo(470.84f, 2635.48f, 474.39f, 2636.95f, 476.89f, 2639.88f);
                    path5036Path.cubicTo(479.41f, 2642.82f, 480.67f, 2646.98f, 480.67f, 2652.35f);
                    path5036Path.lineTo(480.67f, 2655.42f);
                    path5036Path.lineTo(458.58f, 2655.42f);
                    path5036Path.cubicTo(458.81f, 2658.21f, 459.74f, 2660.42f, 461.36f, 2662.04f);
                    path5036Path.cubicTo(463.01f, 2663.67f, 465.07f, 2664.48f, 467.55f, 2664.48f);
                    path5036Path.cubicTo(471.03f, 2664.48f, 473.86f, 2663.07f, 476.05f, 2660.26f);
                    path5036Path.lineTo(480.14f, 2664.17f);
                    path5036Path.cubicTo(478.79f, 2666.19f, 476.98f, 2667.76f, 474.71f, 2668.88f);
                    path5036Path.cubicTo(472.46f, 2669.99f, 469.92f, 2670.54f, 467.11f, 2670.54f);
                    path5036Path.close();
                    path5036Path.moveTo(466.21f, 2641.57f);
                    path5036Path.cubicTo(464.12f, 2641.57f, 462.43f, 2642.3f, 461.14f, 2643.76f);
                    path5036Path.cubicTo(459.87f, 2645.22f, 459.06f, 2647.25f, 458.71f, 2649.85f);
                    path5036Path.lineTo(473.17f, 2649.85f);
                    path5036Path.lineTo(473.17f, 2649.29f);
                    path5036Path.cubicTo(473.01f, 2646.75f, 472.33f, 2644.83f, 471.14f, 2643.54f);
                    path5036Path.cubicTo(469.96f, 2642.23f, 468.31f, 2641.57f, 466.21f, 2641.57f);
                    path5036Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5036Path, paint);

                    // path5038
                    RectF path5038Rect = CacheForCanvas1.path5038Rect;
                    path5038Rect.set(485.27f, 2635.48f, 515.08f, 2683.23f);
                    Path path5038Path = CacheForCanvas1.path5038Path;
                    path5038Path.reset();
                    path5038Path.moveTo(485.27f, 2652.76f);
                    path5038Path.cubicTo(485.27f, 2647.51f, 486.5f, 2643.32f, 488.96f, 2640.2f);
                    path5038Path.cubicTo(491.43f, 2637.05f, 494.72f, 2635.48f, 498.8f, 2635.48f);
                    path5038Path.cubicTo(502.65f, 2635.48f, 505.68f, 2636.82f, 507.89f, 2639.51f);
                    path5038Path.lineTo(508.24f, 2636.1f);
                    path5038Path.lineTo(515.08f, 2636.1f);
                    path5038Path.lineTo(515.08f, 2668.88f);
                    path5038Path.cubicTo(515.08f, 2673.32f, 513.69f, 2676.82f, 510.92f, 2679.38f);
                    path5038Path.cubicTo(508.17f, 2681.95f, 504.46f, 2683.23f, 499.77f, 2683.23f);
                    path5038Path.cubicTo(497.29f, 2683.23f, 494.86f, 2682.71f, 492.49f, 2681.67f);
                    path5038Path.cubicTo(490.13f, 2680.65f, 488.34f, 2679.3f, 487.11f, 2677.63f);
                    path5038Path.lineTo(490.71f, 2673.07f);
                    path5038Path.cubicTo(493.04f, 2675.84f, 495.91f, 2677.23f, 499.33f, 2677.23f);
                    path5038Path.cubicTo(501.85f, 2677.23f, 503.84f, 2676.54f, 505.3f, 2675.17f);
                    path5038Path.cubicTo(506.76f, 2673.81f, 507.49f, 2671.81f, 507.49f, 2669.17f);
                    path5038Path.lineTo(507.49f, 2666.88f);
                    path5038Path.cubicTo(505.3f, 2669.32f, 502.38f, 2670.54f, 498.74f, 2670.54f);
                    path5038Path.cubicTo(494.78f, 2670.54f, 491.54f, 2668.97f, 489.02f, 2665.82f);
                    path5038Path.cubicTo(486.52f, 2662.68f, 485.27f, 2658.32f, 485.27f, 2652.76f);
                    path5038Path.close();
                    path5038Path.moveTo(492.83f, 2653.42f);
                    path5038Path.cubicTo(492.83f, 2656.81f, 493.52f, 2659.49f, 494.89f, 2661.45f);
                    path5038Path.cubicTo(496.29f, 2663.38f, 498.22f, 2664.35f, 500.67f, 2664.35f);
                    path5038Path.cubicTo(503.74f, 2664.35f, 506.01f, 2663.04f, 507.49f, 2660.42f);
                    path5038Path.lineTo(507.49f, 2645.54f);
                    path5038Path.cubicTo(506.05f, 2642.98f, 503.8f, 2641.7f, 500.74f, 2641.7f);
                    path5038Path.cubicTo(498.24f, 2641.7f, 496.29f, 2642.69f, 494.89f, 2644.67f);
                    path5038Path.cubicTo(493.52f, 2646.65f, 492.83f, 2649.56f, 492.83f, 2653.42f);
                    path5038Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5038Path, paint);

                    // path5040
                    RectF path5040Rect = CacheForCanvas1.path5040Rect;
                    path5040Rect.set(522.86f, 2623.26f, 531.46f, 2669.92f);
                    Path path5040Path = CacheForCanvas1.path5040Path;
                    path5040Path.reset();
                    path5040Path.moveTo(530.92f, 2669.92f);
                    path5040Path.lineTo(523.33f, 2669.92f);
                    path5040Path.lineTo(523.33f, 2636.1f);
                    path5040Path.lineTo(530.92f, 2636.1f);
                    path5040Path.lineTo(530.92f, 2669.92f);
                    path5040Path.close();
                    path5040Path.moveTo(522.86f, 2627.32f);
                    path5040Path.cubicTo(522.86f, 2626.16f, 523.23f, 2625.19f, 523.96f, 2624.42f);
                    path5040Path.cubicTo(524.71f, 2623.65f, 525.77f, 2623.26f, 527.14f, 2623.26f);
                    path5040Path.cubicTo(528.52f, 2623.26f, 529.58f, 2623.65f, 530.33f, 2624.42f);
                    path5040Path.cubicTo(531.08f, 2625.19f, 531.46f, 2626.16f, 531.46f, 2627.32f);
                    path5040Path.cubicTo(531.46f, 2628.47f, 531.08f, 2629.43f, 530.33f, 2630.2f);
                    path5040Path.cubicTo(529.58f, 2630.95f, 528.52f, 2631.32f, 527.14f, 2631.32f);
                    path5040Path.cubicTo(525.77f, 2631.32f, 524.71f, 2630.95f, 523.96f, 2630.2f);
                    path5040Path.cubicTo(523.23f, 2629.43f, 522.86f, 2628.47f, 522.86f, 2627.32f);
                    path5040Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5040Path, paint);

                    // path5042
                    RectF path5042Rect = CacheForCanvas1.path5042Rect;
                    path5042Rect.set(537.67f, 2635.48f, 565.64f, 2670.54f);
                    Path path5042Path = CacheForCanvas1.path5042Path;
                    path5042Path.reset();
                    path5042Path.moveTo(558.17f, 2660.73f);
                    path5042Path.cubicTo(558.17f, 2659.37f, 557.61f, 2658.34f, 556.49f, 2657.63f);
                    path5042Path.cubicTo(555.38f, 2656.93f, 553.54f, 2656.3f, 550.96f, 2655.76f);
                    path5042Path.cubicTo(548.37f, 2655.22f, 546.22f, 2654.53f, 544.49f, 2653.7f);
                    path5042Path.cubicTo(540.69f, 2651.86f, 538.8f, 2649.21f, 538.8f, 2645.73f);
                    path5042Path.cubicTo(538.8f, 2642.81f, 540.03f, 2640.37f, 542.49f, 2638.42f);
                    path5042Path.cubicTo(544.94f, 2636.46f, 548.07f, 2635.48f, 551.86f, 2635.48f);
                    path5042Path.cubicTo(555.9f, 2635.48f, 559.16f, 2636.48f, 561.64f, 2638.48f);
                    path5042Path.cubicTo(564.14f, 2640.48f, 565.39f, 2643.07f, 565.39f, 2646.26f);
                    path5042Path.lineTo(557.8f, 2646.26f);
                    path5042Path.cubicTo(557.8f, 2644.8f, 557.26f, 2643.59f, 556.17f, 2642.63f);
                    path5042Path.cubicTo(555.09f, 2641.66f, 553.65f, 2641.17f, 551.86f, 2641.17f);
                    path5042Path.cubicTo(550.19f, 2641.17f, 548.83f, 2641.55f, 547.77f, 2642.32f);
                    path5042Path.cubicTo(546.73f, 2643.09f, 546.21f, 2644.12f, 546.21f, 2645.42f);
                    path5042Path.cubicTo(546.21f, 2646.58f, 546.69f, 2647.49f, 547.67f, 2648.13f);
                    path5042Path.cubicTo(548.65f, 2648.78f, 550.63f, 2649.44f, 553.61f, 2650.1f);
                    path5042Path.cubicTo(556.59f, 2650.75f, 558.92f, 2651.53f, 560.61f, 2652.45f);
                    path5042Path.cubicTo(562.32f, 2653.34f, 563.58f, 2654.43f, 564.39f, 2655.7f);
                    path5042Path.cubicTo(565.23f, 2656.97f, 565.64f, 2658.51f, 565.64f, 2660.32f);
                    path5042Path.cubicTo(565.64f, 2663.36f, 564.38f, 2665.83f, 561.86f, 2667.73f);
                    path5042Path.cubicTo(559.34f, 2669.6f, 556.04f, 2670.54f, 551.96f, 2670.54f);
                    path5042Path.cubicTo(549.18f, 2670.54f, 546.72f, 2670.04f, 544.55f, 2669.04f);
                    path5042Path.cubicTo(542.38f, 2668.04f, 540.69f, 2666.67f, 539.49f, 2664.92f);
                    path5042Path.cubicTo(538.28f, 2663.17f, 537.67f, 2661.28f, 537.67f, 2659.26f);
                    path5042Path.lineTo(545.05f, 2659.26f);
                    path5042Path.cubicTo(545.15f, 2661.05f, 545.83f, 2662.44f, 547.08f, 2663.42f);
                    path5042Path.cubicTo(548.33f, 2664.37f, 549.99f, 2664.85f, 552.05f, 2664.85f);
                    path5042Path.cubicTo(554.05f, 2664.85f, 555.57f, 2664.48f, 556.61f, 2663.73f);
                    path5042Path.cubicTo(557.65f, 2662.96f, 558.17f, 2661.96f, 558.17f, 2660.73f);
                    path5042Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5042Path, paint);

                    // path5044
                    RectF path5044Rect = CacheForCanvas1.path5044Rect;
                    path5044Rect.set(568.58f, 2627.88f, 587.89f, 2670.54f);
                    Path path5044Path = CacheForCanvas1.path5044Path;
                    path5044Path.reset();
                    path5044Path.moveTo(581.74f, 2627.88f);
                    path5044Path.lineTo(581.74f, 2636.1f);
                    path5044Path.lineTo(587.71f, 2636.1f);
                    path5044Path.lineTo(587.71f, 2641.73f);
                    path5044Path.lineTo(581.74f, 2641.73f);
                    path5044Path.lineTo(581.74f, 2660.6f);
                    path5044Path.cubicTo(581.74f, 2661.9f, 581.99f, 2662.83f, 582.49f, 2663.42f);
                    path5044Path.cubicTo(583.01f, 2663.98f, 583.92f, 2664.26f, 585.24f, 2664.26f);
                    path5044Path.cubicTo(586.11f, 2664.26f, 587f, 2664.16f, 587.89f, 2663.95f);
                    path5044Path.lineTo(587.89f, 2669.82f);
                    path5044Path.cubicTo(586.16f, 2670.3f, 584.5f, 2670.54f, 582.89f, 2670.54f);
                    path5044Path.cubicTo(577.06f, 2670.54f, 574.14f, 2667.32f, 574.14f, 2660.88f);
                    path5044Path.lineTo(574.14f, 2641.73f);
                    path5044Path.lineTo(568.58f, 2641.73f);
                    path5044Path.lineTo(568.58f, 2636.1f);
                    path5044Path.lineTo(574.14f, 2636.1f);
                    path5044Path.lineTo(574.14f, 2627.88f);
                    path5044Path.lineTo(581.74f, 2627.88f);
                    path5044Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5044Path, paint);

                    // path5046
                    RectF path5046Rect = CacheForCanvas1.path5046Rect;
                    path5046Rect.set(593.46f, 2635.48f, 611.21f, 2669.92f);
                    Path path5046Path = CacheForCanvas1.path5046Path;
                    path5046Path.reset();
                    path5046Path.moveTo(611.17f, 2643.04f);
                    path5046Path.cubicTo(610.17f, 2642.87f, 609.14f, 2642.79f, 608.08f, 2642.79f);
                    path5046Path.cubicTo(604.6f, 2642.79f, 602.26f, 2644.12f, 601.05f, 2646.79f);
                    path5046Path.lineTo(601.05f, 2669.92f);
                    path5046Path.lineTo(593.46f, 2669.92f);
                    path5046Path.lineTo(593.46f, 2636.1f);
                    path5046Path.lineTo(600.71f, 2636.1f);
                    path5046Path.lineTo(600.89f, 2639.88f);
                    path5046Path.cubicTo(602.73f, 2636.95f, 605.27f, 2635.48f, 608.52f, 2635.48f);
                    path5046Path.cubicTo(609.6f, 2635.48f, 610.5f, 2635.62f, 611.21f, 2635.92f);
                    path5046Path.lineTo(611.17f, 2643.04f);
                    path5046Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5046Path, paint);

                    // path5048
                    RectF path5048Rect = CacheForCanvas1.path5048Rect;
                    path5048Rect.set(614.02f, 2635.48f, 643.05f, 2670.54f);
                    Path path5048Path = CacheForCanvas1.path5048Path;
                    path5048Path.reset();
                    path5048Path.moveTo(635.3f, 2669.92f);
                    path5048Path.cubicTo(634.97f, 2669.27f, 634.67f, 2668.22f, 634.42f, 2666.76f);
                    path5048Path.cubicTo(632.01f, 2669.28f, 629.05f, 2670.54f, 625.55f, 2670.54f);
                    path5048Path.cubicTo(622.15f, 2670.54f, 619.38f, 2669.57f, 617.24f, 2667.63f);
                    path5048Path.cubicTo(615.09f, 2665.7f, 614.02f, 2663.3f, 614.02f, 2660.45f);
                    path5048Path.cubicTo(614.02f, 2656.84f, 615.35f, 2654.08f, 618.02f, 2652.17f);
                    path5048Path.cubicTo(620.71f, 2650.23f, 624.54f, 2649.26f, 629.52f, 2649.26f);
                    path5048Path.lineTo(634.17f, 2649.26f);
                    path5048Path.lineTo(634.17f, 2647.04f);
                    path5048Path.cubicTo(634.17f, 2645.29f, 633.68f, 2643.9f, 632.71f, 2642.85f);
                    path5048Path.cubicTo(631.73f, 2641.79f, 630.24f, 2641.26f, 628.24f, 2641.26f);
                    path5048Path.cubicTo(626.51f, 2641.26f, 625.09f, 2641.7f, 623.99f, 2642.57f);
                    path5048Path.cubicTo(622.88f, 2643.43f, 622.33f, 2644.52f, 622.33f, 2645.85f);
                    path5048Path.lineTo(614.74f, 2645.85f);
                    path5048Path.cubicTo(614.74f, 2644f, 615.35f, 2642.27f, 616.58f, 2640.67f);
                    path5048Path.cubicTo(617.81f, 2639.04f, 619.48f, 2637.77f, 621.58f, 2636.85f);
                    path5048Path.cubicTo(623.71f, 2635.94f, 626.07f, 2635.48f, 628.67f, 2635.48f);
                    path5048Path.cubicTo(632.63f, 2635.48f, 635.79f, 2636.48f, 638.14f, 2638.48f);
                    path5048Path.cubicTo(640.5f, 2640.46f, 641.71f, 2643.25f, 641.77f, 2646.85f);
                    path5048Path.lineTo(641.77f, 2662.1f);
                    path5048Path.cubicTo(641.77f, 2665.15f, 642.19f, 2667.57f, 643.05f, 2669.38f);
                    path5048Path.lineTo(643.05f, 2669.92f);
                    path5048Path.lineTo(635.3f, 2669.92f);
                    path5048Path.close();
                    path5048Path.moveTo(626.96f, 2664.45f);
                    path5048Path.cubicTo(628.46f, 2664.45f, 629.86f, 2664.08f, 631.17f, 2663.35f);
                    path5048Path.cubicTo(632.51f, 2662.62f, 633.51f, 2661.65f, 634.17f, 2660.42f);
                    path5048Path.lineTo(634.17f, 2654.04f);
                    path5048Path.lineTo(630.08f, 2654.04f);
                    path5048Path.cubicTo(627.27f, 2654.04f, 625.15f, 2654.53f, 623.74f, 2655.51f);
                    path5048Path.cubicTo(622.32f, 2656.49f, 621.61f, 2657.87f, 621.61f, 2659.67f);
                    path5048Path.cubicTo(621.61f, 2661.12f, 622.09f, 2662.29f, 623.05f, 2663.17f);
                    path5048Path.cubicTo(624.03f, 2664.02f, 625.33f, 2664.45f, 626.96f, 2664.45f);
                    path5048Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5048Path, paint);

                    // path5050
                    RectF path5050Rect = CacheForCanvas1.path5050Rect;
                    path5050Rect.set(646.08f, 2627.88f, 665.39f, 2670.54f);
                    Path path5050Path = CacheForCanvas1.path5050Path;
                    path5050Path.reset();
                    path5050Path.moveTo(659.24f, 2627.88f);
                    path5050Path.lineTo(659.24f, 2636.1f);
                    path5050Path.lineTo(665.21f, 2636.1f);
                    path5050Path.lineTo(665.21f, 2641.73f);
                    path5050Path.lineTo(659.24f, 2641.73f);
                    path5050Path.lineTo(659.24f, 2660.6f);
                    path5050Path.cubicTo(659.24f, 2661.9f, 659.49f, 2662.83f, 659.99f, 2663.42f);
                    path5050Path.cubicTo(660.51f, 2663.98f, 661.42f, 2664.26f, 662.74f, 2664.26f);
                    path5050Path.cubicTo(663.61f, 2664.26f, 664.5f, 2664.16f, 665.39f, 2663.95f);
                    path5050Path.lineTo(665.39f, 2669.82f);
                    path5050Path.cubicTo(663.66f, 2670.3f, 662f, 2670.54f, 660.39f, 2670.54f);
                    path5050Path.cubicTo(654.56f, 2670.54f, 651.64f, 2667.32f, 651.64f, 2660.88f);
                    path5050Path.lineTo(651.64f, 2641.73f);
                    path5050Path.lineTo(646.08f, 2641.73f);
                    path5050Path.lineTo(646.08f, 2636.1f);
                    path5050Path.lineTo(651.64f, 2636.1f);
                    path5050Path.lineTo(651.64f, 2627.88f);
                    path5050Path.lineTo(659.24f, 2627.88f);
                    path5050Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5050Path, paint);

                    // path5052
                    RectF path5052Rect = CacheForCanvas1.path5052Rect;
                    path5052Rect.set(670.99f, 2623.26f, 679.58f, 2669.92f);
                    Path path5052Path = CacheForCanvas1.path5052Path;
                    path5052Path.reset();
                    path5052Path.moveTo(679.05f, 2669.92f);
                    path5052Path.lineTo(671.46f, 2669.92f);
                    path5052Path.lineTo(671.46f, 2636.1f);
                    path5052Path.lineTo(679.05f, 2636.1f);
                    path5052Path.lineTo(679.05f, 2669.92f);
                    path5052Path.close();
                    path5052Path.moveTo(670.99f, 2627.32f);
                    path5052Path.cubicTo(670.99f, 2626.16f, 671.35f, 2625.19f, 672.08f, 2624.42f);
                    path5052Path.cubicTo(672.83f, 2623.65f, 673.89f, 2623.26f, 675.27f, 2623.26f);
                    path5052Path.cubicTo(676.64f, 2623.26f, 677.71f, 2623.65f, 678.46f, 2624.42f);
                    path5052Path.cubicTo(679.21f, 2625.19f, 679.58f, 2626.16f, 679.58f, 2627.32f);
                    path5052Path.cubicTo(679.58f, 2628.47f, 679.21f, 2629.43f, 678.46f, 2630.2f);
                    path5052Path.cubicTo(677.71f, 2630.95f, 676.64f, 2631.32f, 675.27f, 2631.32f);
                    path5052Path.cubicTo(673.89f, 2631.32f, 672.83f, 2630.95f, 672.08f, 2630.2f);
                    path5052Path.cubicTo(671.35f, 2629.43f, 670.99f, 2628.47f, 670.99f, 2627.32f);
                    path5052Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5052Path, paint);

                    // path5054
                    RectF path5054Rect = CacheForCanvas1.path5054Rect;
                    path5054Rect.set(685.92f, 2635.48f, 717.36f, 2670.54f);
                    Path path5054Path = CacheForCanvas1.path5054Path;
                    path5054Path.reset();
                    path5054Path.moveTo(685.92f, 2652.7f);
                    path5054Path.cubicTo(685.92f, 2649.38f, 686.58f, 2646.41f, 687.89f, 2643.76f);
                    path5054Path.cubicTo(689.21f, 2641.09f, 691.05f, 2639.05f, 693.42f, 2637.63f);
                    path5054Path.cubicTo(695.8f, 2636.2f, 698.53f, 2635.48f, 701.61f, 2635.48f);
                    path5054Path.cubicTo(706.17f, 2635.48f, 709.87f, 2636.95f, 712.71f, 2639.88f);
                    path5054Path.cubicTo(715.56f, 2642.82f, 717.1f, 2646.72f, 717.33f, 2651.57f);
                    path5054Path.lineTo(717.36f, 2653.35f);
                    path5054Path.cubicTo(717.36f, 2656.69f, 716.72f, 2659.67f, 715.42f, 2662.29f);
                    path5054Path.cubicTo(714.15f, 2664.92f, 712.32f, 2666.95f, 709.92f, 2668.38f);
                    path5054Path.cubicTo(707.55f, 2669.82f, 704.8f, 2670.54f, 701.67f, 2670.54f);
                    path5054Path.cubicTo(696.9f, 2670.54f, 693.08f, 2668.96f, 690.21f, 2665.79f);
                    path5054Path.cubicTo(687.35f, 2662.6f, 685.92f, 2658.36f, 685.92f, 2653.07f);
                    path5054Path.lineTo(685.92f, 2652.7f);
                    path5054Path.close();
                    path5054Path.moveTo(693.52f, 2653.35f);
                    path5054Path.cubicTo(693.52f, 2656.83f, 694.24f, 2659.56f, 695.67f, 2661.54f);
                    path5054Path.cubicTo(697.11f, 2663.5f, 699.11f, 2664.48f, 701.67f, 2664.48f);
                    path5054Path.cubicTo(704.24f, 2664.48f, 706.23f, 2663.48f, 707.64f, 2661.48f);
                    path5054Path.cubicTo(709.08f, 2659.48f, 709.8f, 2656.55f, 709.8f, 2652.7f);
                    path5054Path.cubicTo(709.8f, 2649.28f, 709.06f, 2646.57f, 707.58f, 2644.57f);
                    path5054Path.cubicTo(706.12f, 2642.57f, 704.13f, 2641.57f, 701.61f, 2641.57f);
                    path5054Path.cubicTo(699.13f, 2641.57f, 697.16f, 2642.56f, 695.71f, 2644.54f);
                    path5054Path.cubicTo(694.25f, 2646.5f, 693.52f, 2649.44f, 693.52f, 2653.35f);
                    path5054Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5054Path, paint);

                    // path5056
                    RectF path5056Rect = CacheForCanvas1.path5056Rect;
                    path5056Rect.set(723.74f, 2635.48f, 751.71f, 2669.92f);
                    Path path5056Path = CacheForCanvas1.path5056Path;
                    path5056Path.reset();
                    path5056Path.moveTo(730.89f, 2636.1f);
                    path5056Path.lineTo(731.11f, 2640.01f);
                    path5056Path.cubicTo(733.61f, 2636.99f, 736.89f, 2635.48f, 740.96f, 2635.48f);
                    path5056Path.cubicTo(748f, 2635.48f, 751.58f, 2639.51f, 751.71f, 2647.57f);
                    path5056Path.lineTo(751.71f, 2669.92f);
                    path5056Path.lineTo(744.11f, 2669.92f);
                    path5056Path.lineTo(744.11f, 2648.01f);
                    path5056Path.cubicTo(744.11f, 2645.86f, 743.64f, 2644.28f, 742.71f, 2643.26f);
                    path5056Path.cubicTo(741.79f, 2642.22f, 740.28f, 2641.7f, 738.17f, 2641.7f);
                    path5056Path.cubicTo(735.11f, 2641.7f, 732.83f, 2643.08f, 731.33f, 2645.85f);
                    path5056Path.lineTo(731.33f, 2669.92f);
                    path5056Path.lineTo(723.74f, 2669.92f);
                    path5056Path.lineTo(723.74f, 2636.1f);
                    path5056Path.lineTo(730.89f, 2636.1f);
                    path5056Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5056Path, paint);
                }

                // flowRoot3269-7-7-8-0
                {
                    // path5015
                    RectF path5015Rect = CacheForCanvas1.path5015Rect;
                    path5015Rect.set(410.19f, 2729.83f, 446.35f, 2776.58f);
                    Path path5015Path = CacheForCanvas1.path5015Path;
                    path5015Path.reset();
                    path5015Path.moveTo(446.35f, 2761.15f);
                    path5015Path.cubicTo(445.89f, 2766f, 444.1f, 2769.79f, 440.98f, 2772.52f);
                    path5015Path.cubicTo(437.85f, 2775.23f, 433.69f, 2776.58f, 428.51f, 2776.58f);
                    path5015Path.cubicTo(424.88f, 2776.58f, 421.68f, 2775.73f, 418.91f, 2774.02f);
                    path5015Path.cubicTo(416.16f, 2772.29f, 414.04f, 2769.84f, 412.54f, 2766.68f);
                    path5015Path.cubicTo(411.04f, 2763.51f, 410.26f, 2759.83f, 410.19f, 2755.65f);
                    path5015Path.lineTo(410.19f, 2751.4f);
                    path5015Path.cubicTo(410.19f, 2747.1f, 410.96f, 2743.32f, 412.48f, 2740.05f);
                    path5015Path.cubicTo(414f, 2736.78f, 416.17f, 2734.26f, 419.01f, 2732.49f);
                    path5015Path.cubicTo(421.86f, 2730.72f, 425.15f, 2729.83f, 428.88f, 2729.83f);
                    path5015Path.cubicTo(433.9f, 2729.83f, 437.94f, 2731.2f, 441.01f, 2733.93f);
                    path5015Path.cubicTo(444.07f, 2736.66f, 445.85f, 2740.51f, 446.35f, 2745.49f);
                    path5015Path.lineTo(438.48f, 2745.49f);
                    path5015Path.cubicTo(438.1f, 2742.22f, 437.14f, 2739.86f, 435.6f, 2738.43f);
                    path5015Path.cubicTo(434.08f, 2736.97f, 431.84f, 2736.24f, 428.88f, 2736.24f);
                    path5015Path.cubicTo(425.44f, 2736.24f, 422.8f, 2737.5f, 420.94f, 2740.02f);
                    path5015Path.cubicTo(419.11f, 2742.52f, 418.17f, 2746.2f, 418.13f, 2751.05f);
                    path5015Path.lineTo(418.13f, 2755.08f);
                    path5015Path.cubicTo(418.13f, 2760f, 419.01f, 2763.75f, 420.76f, 2766.33f);
                    path5015Path.cubicTo(422.53f, 2768.92f, 425.11f, 2770.21f, 428.51f, 2770.21f);
                    path5015Path.cubicTo(431.61f, 2770.21f, 433.94f, 2769.51f, 435.51f, 2768.11f);
                    path5015Path.cubicTo(437.07f, 2766.72f, 438.06f, 2764.4f, 438.48f, 2761.15f);
                    path5015Path.lineTo(446.35f, 2761.15f);
                    path5015Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5015Path, paint);

                    // path5017
                    RectF path5017Rect = CacheForCanvas1.path5017Rect;
                    path5017Rect.set(453.15f, 2727.95f, 460.75f, 2775.95f);
                    Path path5017Path = CacheForCanvas1.path5017Path;
                    path5017Path.reset();
                    path5017Path.moveTo(path5017Rect.left, path5017Rect.top);
                    path5017Path.lineTo(path5017Rect.right, path5017Rect.top);
                    path5017Path.lineTo(path5017Rect.right, path5017Rect.bottom);
                    path5017Path.lineTo(path5017Rect.left, path5017Rect.bottom);
                    path5017Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5017Path, paint);

                    // path5019
                    RectF path5019Rect = CacheForCanvas1.path5019Rect;
                    path5019Rect.set(467.6f, 2741.52f, 499.04f, 2776.58f);
                    Path path5019Path = CacheForCanvas1.path5019Path;
                    path5019Path.reset();
                    path5019Path.moveTo(467.6f, 2758.74f);
                    path5019Path.cubicTo(467.6f, 2755.43f, 468.26f, 2752.45f, 469.57f, 2749.8f);
                    path5019Path.cubicTo(470.88f, 2747.13f, 472.73f, 2745.09f, 475.1f, 2743.68f);
                    path5019Path.cubicTo(477.48f, 2742.24f, 480.21f, 2741.52f, 483.29f, 2741.52f);
                    path5019Path.cubicTo(487.85f, 2741.52f, 491.55f, 2742.99f, 494.38f, 2745.93f);
                    path5019Path.cubicTo(497.24f, 2748.86f, 498.78f, 2752.76f, 499.01f, 2757.61f);
                    path5019Path.lineTo(499.04f, 2759.4f);
                    path5019Path.cubicTo(499.04f, 2762.73f, 498.39f, 2765.71f, 497.1f, 2768.33f);
                    path5019Path.cubicTo(495.83f, 2770.96f, 494f, 2772.99f, 491.6f, 2774.43f);
                    path5019Path.cubicTo(489.23f, 2775.86f, 486.48f, 2776.58f, 483.35f, 2776.58f);
                    path5019Path.cubicTo(478.58f, 2776.58f, 474.76f, 2775f, 471.88f, 2771.83f);
                    path5019Path.cubicTo(469.03f, 2768.65f, 467.6f, 2764.41f, 467.6f, 2759.11f);
                    path5019Path.lineTo(467.6f, 2758.74f);
                    path5019Path.close();
                    path5019Path.moveTo(475.19f, 2759.4f);
                    path5019Path.cubicTo(475.19f, 2762.87f, 475.91f, 2765.6f, 477.35f, 2767.58f);
                    path5019Path.cubicTo(478.79f, 2769.54f, 480.79f, 2770.52f, 483.35f, 2770.52f);
                    path5019Path.cubicTo(485.91f, 2770.52f, 487.9f, 2769.52f, 489.32f, 2767.52f);
                    path5019Path.cubicTo(490.76f, 2765.52f, 491.48f, 2762.59f, 491.48f, 2758.74f);
                    path5019Path.cubicTo(491.48f, 2755.32f, 490.74f, 2752.61f, 489.26f, 2750.61f);
                    path5019Path.cubicTo(487.8f, 2748.61f, 485.81f, 2747.61f, 483.29f, 2747.61f);
                    path5019Path.cubicTo(480.81f, 2747.61f, 478.84f, 2748.6f, 477.38f, 2750.58f);
                    path5019Path.cubicTo(475.92f, 2752.54f, 475.19f, 2755.48f, 475.19f, 2759.4f);
                    path5019Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5019Path, paint);

                    // path5021
                    RectF path5021Rect = CacheForCanvas1.path5021Rect;
                    path5021Rect.set(504.44f, 2741.52f, 533.48f, 2776.58f);
                    Path path5021Path = CacheForCanvas1.path5021Path;
                    path5021Path.reset();
                    path5021Path.moveTo(525.73f, 2775.96f);
                    path5021Path.cubicTo(525.39f, 2775.31f, 525.1f, 2774.26f, 524.85f, 2772.8f);
                    path5021Path.cubicTo(522.43f, 2775.32f, 519.48f, 2776.58f, 515.98f, 2776.58f);
                    path5021Path.cubicTo(512.58f, 2776.58f, 509.81f, 2775.61f, 507.66f, 2773.68f);
                    path5021Path.cubicTo(505.52f, 2771.74f, 504.44f, 2769.34f, 504.44f, 2766.49f);
                    path5021Path.cubicTo(504.44f, 2762.88f, 505.78f, 2760.12f, 508.44f, 2758.21f);
                    path5021Path.cubicTo(511.13f, 2756.27f, 514.97f, 2755.3f, 519.94f, 2755.3f);
                    path5021Path.lineTo(524.6f, 2755.3f);
                    path5021Path.lineTo(524.6f, 2753.08f);
                    path5021Path.cubicTo(524.6f, 2751.33f, 524.11f, 2749.94f, 523.13f, 2748.9f);
                    path5021Path.cubicTo(522.15f, 2747.83f, 520.66f, 2747.3f, 518.66f, 2747.3f);
                    path5021Path.cubicTo(516.93f, 2747.3f, 515.52f, 2747.74f, 514.41f, 2748.61f);
                    path5021Path.cubicTo(513.31f, 2749.47f, 512.76f, 2750.56f, 512.76f, 2751.9f);
                    path5021Path.lineTo(505.16f, 2751.9f);
                    path5021Path.cubicTo(505.16f, 2750.04f, 505.78f, 2748.31f, 507.01f, 2746.71f);
                    path5021Path.cubicTo(508.24f, 2745.08f, 509.9f, 2743.81f, 512.01f, 2742.9f);
                    path5021Path.cubicTo(514.13f, 2741.98f, 516.5f, 2741.52f, 519.1f, 2741.52f);
                    path5021Path.cubicTo(523.06f, 2741.52f, 526.22f, 2742.52f, 528.57f, 2744.52f);
                    path5021Path.cubicTo(530.92f, 2746.5f, 532.13f, 2749.29f, 532.19f, 2752.9f);
                    path5021Path.lineTo(532.19f, 2768.15f);
                    path5021Path.cubicTo(532.19f, 2771.19f, 532.62f, 2773.61f, 533.48f, 2775.43f);
                    path5021Path.lineTo(533.48f, 2775.96f);
                    path5021Path.lineTo(525.73f, 2775.96f);
                    path5021Path.close();
                    path5021Path.moveTo(517.38f, 2770.49f);
                    path5021Path.cubicTo(518.88f, 2770.49f, 520.29f, 2770.12f, 521.6f, 2769.4f);
                    path5021Path.cubicTo(522.93f, 2768.67f, 523.93f, 2767.69f, 524.6f, 2766.46f);
                    path5021Path.lineTo(524.6f, 2760.08f);
                    path5021Path.lineTo(520.51f, 2760.08f);
                    path5021Path.cubicTo(517.69f, 2760.08f, 515.58f, 2760.57f, 514.16f, 2761.55f);
                    path5021Path.cubicTo(512.75f, 2762.53f, 512.04f, 2763.92f, 512.04f, 2765.71f);
                    path5021Path.cubicTo(512.04f, 2767.17f, 512.52f, 2768.33f, 513.48f, 2769.21f);
                    path5021Path.cubicTo(514.46f, 2770.06f, 515.76f, 2770.49f, 517.38f, 2770.49f);
                    path5021Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5021Path, paint);

                    // path5023
                    RectF path5023Rect = CacheForCanvas1.path5023Rect;
                    path5023Rect.set(540.16f, 2727.96f, 569.94f, 2775.96f);
                    Path path5023Path = CacheForCanvas1.path5023Path;
                    path5023Path.reset();
                    path5023Path.moveTo(551.13f, 2761.46f);
                    path5023Path.lineTo(547.76f, 2764.93f);
                    path5023Path.lineTo(547.76f, 2775.96f);
                    path5023Path.lineTo(540.16f, 2775.96f);
                    path5023Path.lineTo(540.16f, 2727.96f);
                    path5023Path.lineTo(547.76f, 2727.96f);
                    path5023Path.lineTo(547.76f, 2755.65f);
                    path5023Path.lineTo(550.13f, 2752.68f);
                    path5023Path.lineTo(559.48f, 2742.15f);
                    path5023Path.lineTo(568.6f, 2742.15f);
                    path5023Path.lineTo(556.04f, 2756.24f);
                    path5023Path.lineTo(569.94f, 2775.96f);
                    path5023Path.lineTo(561.16f, 2775.96f);
                    path5023Path.lineTo(551.13f, 2761.46f);
                    path5023Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5023Path, paint);

                    // path5025
                    RectF path5025Rect = CacheForCanvas1.path5025Rect;
                    path5025Rect.set(573.51f, 2741.52f, 591.26f, 2775.96f);
                    Path path5025Path = CacheForCanvas1.path5025Path;
                    path5025Path.reset();
                    path5025Path.moveTo(591.23f, 2749.08f);
                    path5025Path.cubicTo(590.23f, 2748.92f, 589.19f, 2748.83f, 588.13f, 2748.83f);
                    path5025Path.cubicTo(584.65f, 2748.83f, 582.31f, 2750.17f, 581.1f, 2752.83f);
                    path5025Path.lineTo(581.1f, 2775.96f);
                    path5025Path.lineTo(573.51f, 2775.96f);
                    path5025Path.lineTo(573.51f, 2742.15f);
                    path5025Path.lineTo(580.76f, 2742.15f);
                    path5025Path.lineTo(580.94f, 2745.93f);
                    path5025Path.cubicTo(582.78f, 2742.99f, 585.32f, 2741.52f, 588.57f, 2741.52f);
                    path5025Path.cubicTo(589.65f, 2741.52f, 590.55f, 2741.67f, 591.26f, 2741.96f);
                    path5025Path.lineTo(591.23f, 2749.08f);
                    path5025Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5025Path, paint);

                    // path5027
                    RectF path5027Rect = CacheForCanvas1.path5027Rect;
                    path5027Rect.set(593.48f, 2741.52f, 624.91f, 2776.58f);
                    Path path5027Path = CacheForCanvas1.path5027Path;
                    path5027Path.reset();
                    path5027Path.moveTo(593.48f, 2758.74f);
                    path5027Path.cubicTo(593.48f, 2755.43f, 594.13f, 2752.45f, 595.44f, 2749.8f);
                    path5027Path.cubicTo(596.76f, 2747.13f, 598.6f, 2745.09f, 600.98f, 2743.68f);
                    path5027Path.cubicTo(603.35f, 2742.24f, 606.08f, 2741.52f, 609.16f, 2741.52f);
                    path5027Path.cubicTo(613.73f, 2741.52f, 617.42f, 2742.99f, 620.26f, 2745.93f);
                    path5027Path.cubicTo(623.11f, 2748.86f, 624.65f, 2752.76f, 624.88f, 2757.61f);
                    path5027Path.lineTo(624.91f, 2759.4f);
                    path5027Path.cubicTo(624.91f, 2762.73f, 624.27f, 2765.71f, 622.98f, 2768.33f);
                    path5027Path.cubicTo(621.71f, 2770.96f, 619.87f, 2772.99f, 617.48f, 2774.43f);
                    path5027Path.cubicTo(615.1f, 2775.86f, 612.35f, 2776.58f, 609.23f, 2776.58f);
                    path5027Path.cubicTo(604.46f, 2776.58f, 600.63f, 2775f, 597.76f, 2771.83f);
                    path5027Path.cubicTo(594.9f, 2768.65f, 593.48f, 2764.41f, 593.48f, 2759.11f);
                    path5027Path.lineTo(593.48f, 2758.74f);
                    path5027Path.close();
                    path5027Path.moveTo(601.07f, 2759.4f);
                    path5027Path.cubicTo(601.07f, 2762.87f, 601.79f, 2765.6f, 603.23f, 2767.58f);
                    path5027Path.cubicTo(604.66f, 2769.54f, 606.66f, 2770.52f, 609.23f, 2770.52f);
                    path5027Path.cubicTo(611.79f, 2770.52f, 613.78f, 2769.52f, 615.19f, 2767.52f);
                    path5027Path.cubicTo(616.63f, 2765.52f, 617.35f, 2762.59f, 617.35f, 2758.74f);
                    path5027Path.cubicTo(617.35f, 2755.32f, 616.61f, 2752.61f, 615.13f, 2750.61f);
                    path5027Path.cubicTo(613.67f, 2748.61f, 611.68f, 2747.61f, 609.16f, 2747.61f);
                    path5027Path.cubicTo(606.68f, 2747.61f, 604.72f, 2748.6f, 603.26f, 2750.58f);
                    path5027Path.cubicTo(601.8f, 2752.54f, 601.07f, 2755.48f, 601.07f, 2759.4f);
                    path5027Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5027Path, paint);

                    // path5029
                    RectF path5029Rect = CacheForCanvas1.path5029Rect;
                    path5029Rect.set(629.98f, 2741.52f, 661.41f, 2776.58f);
                    Path path5029Path = CacheForCanvas1.path5029Path;
                    path5029Path.reset();
                    path5029Path.moveTo(629.98f, 2758.74f);
                    path5029Path.cubicTo(629.98f, 2755.43f, 630.63f, 2752.45f, 631.94f, 2749.8f);
                    path5029Path.cubicTo(633.26f, 2747.13f, 635.1f, 2745.09f, 637.48f, 2743.68f);
                    path5029Path.cubicTo(639.85f, 2742.24f, 642.58f, 2741.52f, 645.66f, 2741.52f);
                    path5029Path.cubicTo(650.23f, 2741.52f, 653.92f, 2742.99f, 656.76f, 2745.93f);
                    path5029Path.cubicTo(659.61f, 2748.86f, 661.15f, 2752.76f, 661.38f, 2757.61f);
                    path5029Path.lineTo(661.41f, 2759.4f);
                    path5029Path.cubicTo(661.41f, 2762.73f, 660.77f, 2765.71f, 659.48f, 2768.33f);
                    path5029Path.cubicTo(658.21f, 2770.96f, 656.37f, 2772.99f, 653.98f, 2774.43f);
                    path5029Path.cubicTo(651.6f, 2775.86f, 648.85f, 2776.58f, 645.73f, 2776.58f);
                    path5029Path.cubicTo(640.96f, 2776.58f, 637.13f, 2775f, 634.26f, 2771.83f);
                    path5029Path.cubicTo(631.4f, 2768.65f, 629.98f, 2764.41f, 629.98f, 2759.11f);
                    path5029Path.lineTo(629.98f, 2758.74f);
                    path5029Path.close();
                    path5029Path.moveTo(637.57f, 2759.4f);
                    path5029Path.cubicTo(637.57f, 2762.87f, 638.29f, 2765.6f, 639.73f, 2767.58f);
                    path5029Path.cubicTo(641.16f, 2769.54f, 643.16f, 2770.52f, 645.73f, 2770.52f);
                    path5029Path.cubicTo(648.29f, 2770.52f, 650.28f, 2769.52f, 651.69f, 2767.52f);
                    path5029Path.cubicTo(653.13f, 2765.52f, 653.85f, 2762.59f, 653.85f, 2758.74f);
                    path5029Path.cubicTo(653.85f, 2755.32f, 653.11f, 2752.61f, 651.63f, 2750.61f);
                    path5029Path.cubicTo(650.17f, 2748.61f, 648.18f, 2747.61f, 645.66f, 2747.61f);
                    path5029Path.cubicTo(643.18f, 2747.61f, 641.22f, 2748.6f, 639.76f, 2750.58f);
                    path5029Path.cubicTo(638.3f, 2752.54f, 637.57f, 2755.48f, 637.57f, 2759.4f);
                    path5029Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5029Path, paint);

                    // path5031
                    RectF path5031Rect = CacheForCanvas1.path5031Rect;
                    path5031Rect.set(667.88f, 2741.52f, 715.79f, 2775.96f);
                    Path path5031Path = CacheForCanvas1.path5031Path;
                    path5031Path.reset();
                    path5031Path.moveTo(675.04f, 2742.15f);
                    path5031Path.lineTo(675.26f, 2745.68f);
                    path5031Path.cubicTo(677.63f, 2742.91f, 680.88f, 2741.52f, 685.01f, 2741.52f);
                    path5031Path.cubicTo(689.53f, 2741.52f, 692.62f, 2743.25f, 694.29f, 2746.71f);
                    path5031Path.cubicTo(696.75f, 2743.25f, 700.21f, 2741.52f, 704.66f, 2741.52f);
                    path5031Path.cubicTo(708.39f, 2741.52f, 711.16f, 2742.55f, 712.98f, 2744.61f);
                    path5031Path.cubicTo(714.81f, 2746.68f, 715.75f, 2749.72f, 715.79f, 2753.74f);
                    path5031Path.lineTo(715.79f, 2775.96f);
                    path5031Path.lineTo(708.19f, 2775.96f);
                    path5031Path.lineTo(708.19f, 2753.96f);
                    path5031Path.cubicTo(708.19f, 2751.81f, 707.73f, 2750.24f, 706.79f, 2749.24f);
                    path5031Path.cubicTo(705.85f, 2748.24f, 704.3f, 2747.74f, 702.13f, 2747.74f);
                    path5031Path.cubicTo(700.4f, 2747.74f, 698.99f, 2748.21f, 697.88f, 2749.15f);
                    path5031Path.cubicTo(696.8f, 2750.06f, 696.04f, 2751.27f, 695.6f, 2752.77f);
                    path5031Path.lineTo(695.63f, 2775.96f);
                    path5031Path.lineTo(688.04f, 2775.96f);
                    path5031Path.lineTo(688.04f, 2753.71f);
                    path5031Path.cubicTo(687.93f, 2749.73f, 685.9f, 2747.74f, 681.94f, 2747.74f);
                    path5031Path.cubicTo(678.9f, 2747.74f, 676.75f, 2748.98f, 675.48f, 2751.46f);
                    path5031Path.lineTo(675.48f, 2775.96f);
                    path5031Path.lineTo(667.88f, 2775.96f);
                    path5031Path.lineTo(667.88f, 2742.15f);
                    path5031Path.lineTo(675.04f, 2742.15f);
                    path5031Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5031Path, paint);
                }

                // flowRoot3269-7-7-8-0-5
                {
                    // path5162
                    RectF path5162Rect = CacheForCanvas1.path5162Rect;
                    path5162Rect.set(0.06f, 1351.36f, 29.81f, 1396.86f);
                    Path path5162Path = CacheForCanvas1.path5162Path;
                    path5162Path.reset();
                    path5162Path.moveTo(26.65f, 1376.54f);
                    path5162Path.lineTo(7.96f, 1376.54f);
                    path5162Path.lineTo(7.96f, 1390.54f);
                    path5162Path.lineTo(29.81f, 1390.54f);
                    path5162Path.lineTo(29.81f, 1396.86f);
                    path5162Path.lineTo(0.06f, 1396.86f);
                    path5162Path.lineTo(0.06f, 1351.36f);
                    path5162Path.lineTo(29.59f, 1351.36f);
                    path5162Path.lineTo(29.59f, 1357.73f);
                    path5162Path.lineTo(7.96f, 1357.73f);
                    path5162Path.lineTo(7.96f, 1370.29f);
                    path5162Path.lineTo(26.65f, 1370.29f);
                    path5162Path.lineTo(26.65f, 1376.54f);
                    path5162Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5162Path, paint);

                    // path5164
                    RectF path5164Rect = CacheForCanvas1.path5164Rect;
                    path5164Rect.set(35.46f, 1362.42f, 63.43f, 1396.86f);
                    Path path5164Path = CacheForCanvas1.path5164Path;
                    path5164Path.reset();
                    path5164Path.moveTo(42.62f, 1363.04f);
                    path5164Path.lineTo(42.84f, 1366.95f);
                    path5164Path.cubicTo(45.34f, 1363.93f, 48.62f, 1362.42f, 52.68f, 1362.42f);
                    path5164Path.cubicTo(59.72f, 1362.42f, 63.31f, 1366.45f, 63.43f, 1374.51f);
                    path5164Path.lineTo(63.43f, 1396.86f);
                    path5164Path.lineTo(55.84f, 1396.86f);
                    path5164Path.lineTo(55.84f, 1374.95f);
                    path5164Path.cubicTo(55.84f, 1372.8f, 55.37f, 1371.22f, 54.43f, 1370.2f);
                    path5164Path.cubicTo(53.51f, 1369.16f, 52f, 1368.64f, 49.9f, 1368.64f);
                    path5164Path.cubicTo(46.84f, 1368.64f, 44.56f, 1370.02f, 43.06f, 1372.79f);
                    path5164Path.lineTo(43.06f, 1396.86f);
                    path5164Path.lineTo(35.46f, 1396.86f);
                    path5164Path.lineTo(35.46f, 1363.04f);
                    path5164Path.lineTo(42.62f, 1363.04f);
                    path5164Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5164Path, paint);

                    // path5166
                    RectF path5166Rect = CacheForCanvas1.path5166Rect;
                    path5166Rect.set(67.56f, 1354.83f, 86.87f, 1397.48f);
                    Path path5166Path = CacheForCanvas1.path5166Path;
                    path5166Path.reset();
                    path5166Path.moveTo(80.71f, 1354.83f);
                    path5166Path.lineTo(80.71f, 1363.04f);
                    path5166Path.lineTo(86.68f, 1363.04f);
                    path5166Path.lineTo(86.68f, 1368.67f);
                    path5166Path.lineTo(80.71f, 1368.67f);
                    path5166Path.lineTo(80.71f, 1387.54f);
                    path5166Path.cubicTo(80.71f, 1388.84f, 80.96f, 1389.77f, 81.46f, 1390.36f);
                    path5166Path.cubicTo(81.98f, 1390.92f, 82.9f, 1391.2f, 84.21f, 1391.2f);
                    path5166Path.cubicTo(85.09f, 1391.2f, 85.97f, 1391.1f, 86.87f, 1390.89f);
                    path5166Path.lineTo(86.87f, 1396.76f);
                    path5166Path.cubicTo(85.14f, 1397.24f, 83.47f, 1397.48f, 81.87f, 1397.48f);
                    path5166Path.cubicTo(76.03f, 1397.48f, 73.12f, 1394.26f, 73.12f, 1387.83f);
                    path5166Path.lineTo(73.12f, 1368.67f);
                    path5166Path.lineTo(67.56f, 1368.67f);
                    path5166Path.lineTo(67.56f, 1363.04f);
                    path5166Path.lineTo(73.12f, 1363.04f);
                    path5166Path.lineTo(73.12f, 1354.83f);
                    path5166Path.lineTo(80.71f, 1354.83f);
                    path5166Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5166Path, paint);

                    // path5168
                    RectF path5168Rect = CacheForCanvas1.path5168Rect;
                    path5168Rect.set(92.43f, 1362.42f, 110.18f, 1396.86f);
                    Path path5168Path = CacheForCanvas1.path5168Path;
                    path5168Path.reset();
                    path5168Path.moveTo(110.15f, 1369.98f);
                    path5168Path.cubicTo(109.15f, 1369.82f, 108.12f, 1369.73f, 107.06f, 1369.73f);
                    path5168Path.cubicTo(103.58f, 1369.73f, 101.23f, 1371.07f, 100.02f, 1373.73f);
                    path5168Path.lineTo(100.02f, 1396.86f);
                    path5168Path.lineTo(92.43f, 1396.86f);
                    path5168Path.lineTo(92.43f, 1363.04f);
                    path5168Path.lineTo(99.68f, 1363.04f);
                    path5168Path.lineTo(99.87f, 1366.83f);
                    path5168Path.cubicTo(101.7f, 1363.89f, 104.24f, 1362.42f, 107.49f, 1362.42f);
                    path5168Path.cubicTo(108.58f, 1362.42f, 109.47f, 1362.57f, 110.18f, 1362.86f);
                    path5168Path.lineTo(110.15f, 1369.98f);
                    path5168Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5168Path, paint);

                    // path5170
                    RectF path5170Rect = CacheForCanvas1.path5170Rect;
                    path5170Rect.set(112.99f, 1362.42f, 142.02f, 1397.48f);
                    Path path5170Path = CacheForCanvas1.path5170Path;
                    path5170Path.reset();
                    path5170Path.moveTo(134.27f, 1396.86f);
                    path5170Path.cubicTo(133.94f, 1396.21f, 133.65f, 1395.16f, 133.4f, 1393.7f);
                    path5170Path.cubicTo(130.98f, 1396.22f, 128.02f, 1397.48f, 124.52f, 1397.48f);
                    path5170Path.cubicTo(121.13f, 1397.48f, 118.36f, 1396.51f, 116.21f, 1394.58f);
                    path5170Path.cubicTo(114.07f, 1392.64f, 112.99f, 1390.24f, 112.99f, 1387.39f);
                    path5170Path.cubicTo(112.99f, 1383.78f, 114.33f, 1381.02f, 116.99f, 1379.11f);
                    path5170Path.cubicTo(119.68f, 1377.17f, 123.51f, 1376.2f, 128.49f, 1376.2f);
                    path5170Path.lineTo(133.15f, 1376.2f);
                    path5170Path.lineTo(133.15f, 1373.98f);
                    path5170Path.cubicTo(133.15f, 1372.23f, 132.66f, 1370.84f, 131.68f, 1369.79f);
                    path5170Path.cubicTo(130.7f, 1368.73f, 129.21f, 1368.2f, 127.21f, 1368.2f);
                    path5170Path.cubicTo(125.48f, 1368.2f, 124.07f, 1368.64f, 122.96f, 1369.51f);
                    path5170Path.cubicTo(121.86f, 1370.37f, 121.31f, 1371.46f, 121.31f, 1372.79f);
                    path5170Path.lineTo(113.71f, 1372.79f);
                    path5170Path.cubicTo(113.71f, 1370.94f, 114.33f, 1369.21f, 115.56f, 1367.61f);
                    path5170Path.cubicTo(116.78f, 1365.98f, 118.45f, 1364.71f, 120.56f, 1363.79f);
                    path5170Path.cubicTo(122.68f, 1362.88f, 125.05f, 1362.42f, 127.65f, 1362.42f);
                    path5170Path.cubicTo(131.61f, 1362.42f, 134.76f, 1363.42f, 137.12f, 1365.42f);
                    path5170Path.cubicTo(139.47f, 1367.4f, 140.68f, 1370.19f, 140.74f, 1373.79f);
                    path5170Path.lineTo(140.74f, 1389.04f);
                    path5170Path.cubicTo(140.74f, 1392.09f, 141.17f, 1394.51f, 142.02f, 1396.33f);
                    path5170Path.lineTo(142.02f, 1396.86f);
                    path5170Path.lineTo(134.27f, 1396.86f);
                    path5170Path.close();
                    path5170Path.moveTo(125.93f, 1391.39f);
                    path5170Path.cubicTo(127.43f, 1391.39f, 128.84f, 1391.02f, 130.15f, 1390.29f);
                    path5170Path.cubicTo(131.48f, 1389.57f, 132.48f, 1388.59f, 133.15f, 1387.36f);
                    path5170Path.lineTo(133.15f, 1380.98f);
                    path5170Path.lineTo(129.06f, 1380.98f);
                    path5170Path.cubicTo(126.24f, 1380.98f, 124.13f, 1381.47f, 122.71f, 1382.45f);
                    path5170Path.cubicTo(121.3f, 1383.43f, 120.59f, 1384.82f, 120.59f, 1386.61f);
                    path5170Path.cubicTo(120.59f, 1388.07f, 121.07f, 1389.23f, 122.02f, 1390.11f);
                    path5170Path.cubicTo(123f, 1390.96f, 124.31f, 1391.39f, 125.93f, 1391.39f);
                    path5170Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5170Path, paint);

                    // path5172
                    RectF path5172Rect = CacheForCanvas1.path5172Rect;
                    path5172Rect.set(148.59f, 1362.42f, 176.56f, 1396.86f);
                    Path path5172Path = CacheForCanvas1.path5172Path;
                    path5172Path.reset();
                    path5172Path.moveTo(155.74f, 1363.04f);
                    path5172Path.lineTo(155.96f, 1366.95f);
                    path5172Path.cubicTo(158.46f, 1363.93f, 161.74f, 1362.42f, 165.81f, 1362.42f);
                    path5172Path.cubicTo(172.85f, 1362.42f, 176.43f, 1366.45f, 176.56f, 1374.51f);
                    path5172Path.lineTo(176.56f, 1396.86f);
                    path5172Path.lineTo(168.96f, 1396.86f);
                    path5172Path.lineTo(168.96f, 1374.95f);
                    path5172Path.cubicTo(168.96f, 1372.8f, 168.49f, 1371.22f, 167.56f, 1370.2f);
                    path5172Path.cubicTo(166.64f, 1369.16f, 165.13f, 1368.64f, 163.02f, 1368.64f);
                    path5172Path.cubicTo(159.96f, 1368.64f, 157.68f, 1370.02f, 156.18f, 1372.79f);
                    path5172Path.lineTo(156.18f, 1396.86f);
                    path5172Path.lineTo(148.59f, 1396.86f);
                    path5172Path.lineTo(148.59f, 1363.04f);
                    path5172Path.lineTo(155.74f, 1363.04f);
                    path5172Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5172Path, paint);

                    // path5174
                    RectF path5174Rect = CacheForCanvas1.path5174Rect;
                    path5174Rect.set(182.9f, 1362.42f, 212.09f, 1397.48f);
                    Path path5174Path = CacheForCanvas1.path5174Path;
                    path5174Path.reset();
                    path5174Path.moveTo(198.21f, 1391.42f);
                    path5174Path.cubicTo(200.11f, 1391.42f, 201.68f, 1390.87f, 202.93f, 1389.76f);
                    path5174Path.cubicTo(204.18f, 1388.66f, 204.85f, 1387.29f, 204.93f, 1385.67f);
                    path5174Path.lineTo(212.09f, 1385.67f);
                    path5174Path.cubicTo(212f, 1387.77f, 211.35f, 1389.74f, 210.12f, 1391.58f);
                    path5174Path.cubicTo(208.89f, 1393.39f, 207.22f, 1394.83f, 205.12f, 1395.89f);
                    path5174Path.cubicTo(203.01f, 1396.95f, 200.74f, 1397.48f, 198.31f, 1397.48f);
                    path5174Path.cubicTo(193.58f, 1397.48f, 189.83f, 1395.95f, 187.06f, 1392.89f);
                    path5174Path.cubicTo(184.28f, 1389.83f, 182.9f, 1385.6f, 182.9f, 1380.2f);
                    path5174Path.lineTo(182.9f, 1379.42f);
                    path5174Path.cubicTo(182.9f, 1374.27f, 184.27f, 1370.16f, 187.02f, 1367.08f);
                    path5174Path.cubicTo(189.77f, 1363.97f, 193.52f, 1362.42f, 198.27f, 1362.42f);
                    path5174Path.cubicTo(202.3f, 1362.42f, 205.57f, 1363.6f, 208.09f, 1365.95f);
                    path5174Path.cubicTo(210.63f, 1368.28f, 211.96f, 1371.36f, 212.09f, 1375.17f);
                    path5174Path.lineTo(204.93f, 1375.17f);
                    path5174Path.cubicTo(204.85f, 1373.23f, 204.18f, 1371.64f, 202.93f, 1370.39f);
                    path5174Path.cubicTo(201.7f, 1369.14f, 200.13f, 1368.51f, 198.21f, 1368.51f);
                    path5174Path.cubicTo(195.75f, 1368.51f, 193.86f, 1369.41f, 192.52f, 1371.2f);
                    path5174Path.cubicTo(191.19f, 1372.97f, 190.51f, 1375.67f, 190.49f, 1379.29f);
                    path5174Path.lineTo(190.49f, 1380.51f);
                    path5174Path.cubicTo(190.49f, 1384.18f, 191.15f, 1386.92f, 192.46f, 1388.73f);
                    path5174Path.cubicTo(193.8f, 1390.52f, 195.71f, 1391.42f, 198.21f, 1391.42f);
                    path5174Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5174Path, paint);

                    // path5176
                    RectF path5176Rect = CacheForCanvas1.path5176Rect;
                    path5176Rect.set(216.52f, 1362.42f, 246.27f, 1397.48f);
                    Path path5176Path = CacheForCanvas1.path5176Path;
                    path5176Path.reset();
                    path5176Path.moveTo(232.71f, 1397.48f);
                    path5176Path.cubicTo(227.9f, 1397.48f, 223.99f, 1395.97f, 220.99f, 1392.95f);
                    path5176Path.cubicTo(218.01f, 1389.91f, 216.52f, 1385.87f, 216.52f, 1380.83f);
                    path5176Path.lineTo(216.52f, 1379.89f);
                    path5176Path.cubicTo(216.52f, 1376.51f, 217.17f, 1373.5f, 218.46f, 1370.86f);
                    path5176Path.cubicTo(219.77f, 1368.19f, 221.61f, 1366.12f, 223.96f, 1364.64f);
                    path5176Path.cubicTo(226.32f, 1363.16f, 228.94f, 1362.42f, 231.84f, 1362.42f);
                    path5176Path.cubicTo(236.44f, 1362.42f, 239.99f, 1363.89f, 242.49f, 1366.83f);
                    path5176Path.cubicTo(245.01f, 1369.76f, 246.27f, 1373.92f, 246.27f, 1379.29f);
                    path5176Path.lineTo(246.27f, 1382.36f);
                    path5176Path.lineTo(224.18f, 1382.36f);
                    path5176Path.cubicTo(224.41f, 1385.15f, 225.34f, 1387.36f, 226.96f, 1388.98f);
                    path5176Path.cubicTo(228.61f, 1390.61f, 230.67f, 1391.42f, 233.15f, 1391.42f);
                    path5176Path.cubicTo(236.63f, 1391.42f, 239.46f, 1390.01f, 241.65f, 1387.2f);
                    path5176Path.lineTo(245.74f, 1391.11f);
                    path5176Path.cubicTo(244.39f, 1393.13f, 242.58f, 1394.7f, 240.31f, 1395.83f);
                    path5176Path.cubicTo(238.06f, 1396.93f, 235.52f, 1397.48f, 232.71f, 1397.48f);
                    path5176Path.close();
                    path5176Path.moveTo(231.81f, 1368.51f);
                    path5176Path.cubicTo(229.72f, 1368.51f, 228.03f, 1369.24f, 226.74f, 1370.7f);
                    path5176Path.cubicTo(225.47f, 1372.16f, 224.66f, 1374.19f, 224.31f, 1376.79f);
                    path5176Path.lineTo(238.77f, 1376.79f);
                    path5176Path.lineTo(238.77f, 1376.23f);
                    path5176Path.cubicTo(238.61f, 1373.69f, 237.93f, 1371.77f, 236.74f, 1370.48f);
                    path5176Path.cubicTo(235.56f, 1369.17f, 233.91f, 1368.51f, 231.81f, 1368.51f);
                    path5176Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5176Path, paint);
                }

                // flowRoot3269-9
                {
                    // path5142
                    RectF path5142Rect = CacheForCanvas1.path5142Rect;
                    path5142Rect.set(1631.51f, 739.32f, 1687.67f, 876.29f);
                    Path path5142Path = CacheForCanvas1.path5142Path;
                    path5142Path.reset();
                    path5142Path.moveTo(1687.67f, 876.29f);
                    path5142Path.lineTo(1664.98f, 876.29f);
                    path5142Path.lineTo(1664.98f, 766.69f);
                    path5142Path.lineTo(1631.51f, 778.13f);
                    path5142Path.lineTo(1631.51f, 758.91f);
                    path5142Path.lineTo(1684.76f, 739.32f);
                    path5142Path.lineTo(1687.67f, 739.32f);
                    path5142Path.lineTo(1687.67f, 876.29f);
                    path5142Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5142Path, paint);
                }

                // flowRoot3269-9-2
                {
                    // path5106
                    RectF path5106Rect = CacheForCanvas1.path5106Rect;
                    path5106Rect.set(1614.92f, 1326.77f, 1704.26f, 1467.02f);
                    Path path5106Path = CacheForCanvas1.path5106Path;
                    path5106Path.reset();
                    path5106Path.moveTo(1704.26f, 1407.67f);
                    path5106Path.cubicTo(1704.26f, 1427.3f, 1700.57f, 1442.11f, 1693.2f, 1452.11f);
                    path5106Path.cubicTo(1685.88f, 1462.05f, 1674.73f, 1467.02f, 1659.73f, 1467.02f);
                    path5106Path.cubicTo(1644.98f, 1467.02f, 1633.85f, 1462.17f, 1626.35f, 1452.48f);
                    path5106Path.cubicTo(1618.92f, 1442.73f, 1615.1f, 1428.33f, 1614.92f, 1409.27f);
                    path5106Path.lineTo(1614.92f, 1385.64f);
                    path5106Path.cubicTo(1614.92f, 1366.02f, 1618.57f, 1351.3f, 1625.88f, 1341.48f);
                    path5106Path.cubicTo(1633.26f, 1331.67f, 1644.48f, 1326.77f, 1659.54f, 1326.77f);
                    path5106Path.cubicTo(1674.48f, 1326.77f, 1685.6f, 1331.55f, 1692.92f, 1341.11f);
                    path5106Path.cubicTo(1700.29f, 1350.67f, 1704.07f, 1364.98f, 1704.26f, 1384.05f);
                    path5106Path.lineTo(1704.26f, 1407.67f);
                    path5106Path.close();
                    path5106Path.moveTo(1681.48f, 1382.17f);
                    path5106Path.cubicTo(1681.48f, 1369.36f, 1679.73f, 1359.98f, 1676.23f, 1354.05f);
                    path5106Path.cubicTo(1672.73f, 1348.05f, 1667.17f, 1345.05f, 1659.54f, 1345.05f);
                    path5106Path.cubicTo(1652.1f, 1345.05f, 1646.63f, 1347.89f, 1643.13f, 1353.58f);
                    path5106Path.cubicTo(1639.63f, 1359.2f, 1637.79f, 1368.02f, 1637.6f, 1380.02f);
                    path5106Path.lineTo(1637.6f, 1410.95f);
                    path5106Path.cubicTo(1637.6f, 1423.7f, 1639.38f, 1433.2f, 1642.95f, 1439.45f);
                    path5106Path.cubicTo(1646.51f, 1445.7f, 1652.1f, 1448.83f, 1659.73f, 1448.83f);
                    path5106Path.cubicTo(1667.04f, 1448.83f, 1672.45f, 1445.95f, 1675.95f, 1440.2f);
                    path5106Path.cubicTo(1679.45f, 1434.39f, 1681.29f, 1425.33f, 1681.48f, 1413.02f);
                    path5106Path.lineTo(1681.48f, 1382.17f);
                    path5106Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5106Path, paint);
                }

                // flowRoot3269-9-9
                {
                    // path5109
                    RectF path5109Rect = CacheForCanvas1.path5109Rect;
                    path5109Rect.set(1548.4f, 1980.5f, 1597.6f, 1998.85f);
                    Path path5109Path = CacheForCanvas1.path5109Path;
                    path5109Path.reset();
                    path5109Path.moveTo(path5109Rect.left, path5109Rect.top);
                    path5109Path.lineTo(path5109Rect.right, path5109Rect.top);
                    path5109Path.lineTo(path5109Rect.right, path5109Rect.bottom);
                    path5109Path.lineTo(path5109Rect.left, path5109Rect.bottom);
                    path5109Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5109Path, paint);

                    // path5111
                    RectF path5111Rect = CacheForCanvas1.path5111Rect;
                    path5111Rect.set(1614.57f, 1909.37f, 1703.92f, 2049.62f);
                    Path path5111Path = CacheForCanvas1.path5111Path;
                    path5111Path.reset();
                    path5111Path.moveTo(1703.92f, 1990.27f);
                    path5111Path.cubicTo(1703.92f, 2009.9f, 1700.23f, 2024.71f, 1692.85f, 2034.71f);
                    path5111Path.cubicTo(1685.54f, 2044.65f, 1674.38f, 2049.62f, 1659.38f, 2049.62f);
                    path5111Path.cubicTo(1644.63f, 2049.62f, 1633.51f, 2044.77f, 1626.01f, 2035.09f);
                    path5111Path.cubicTo(1618.57f, 2025.34f, 1614.76f, 2010.93f, 1614.57f, 1991.87f);
                    path5111Path.lineTo(1614.57f, 1968.24f);
                    path5111Path.cubicTo(1614.57f, 1948.62f, 1618.23f, 1933.9f, 1625.54f, 1924.09f);
                    path5111Path.cubicTo(1632.92f, 1914.27f, 1644.13f, 1909.37f, 1659.2f, 1909.37f);
                    path5111Path.cubicTo(1674.13f, 1909.37f, 1685.26f, 1914.15f, 1692.57f, 1923.71f);
                    path5111Path.cubicTo(1699.95f, 1933.27f, 1703.73f, 1947.59f, 1703.92f, 1966.65f);
                    path5111Path.lineTo(1703.92f, 1990.27f);
                    path5111Path.close();
                    path5111Path.moveTo(1681.13f, 1964.77f);
                    path5111Path.cubicTo(1681.13f, 1951.96f, 1679.38f, 1942.59f, 1675.88f, 1936.65f);
                    path5111Path.cubicTo(1672.38f, 1930.65f, 1666.82f, 1927.65f, 1659.2f, 1927.65f);
                    path5111Path.cubicTo(1651.76f, 1927.65f, 1646.29f, 1930.49f, 1642.79f, 1936.18f);
                    path5111Path.cubicTo(1639.29f, 1941.81f, 1637.45f, 1950.62f, 1637.26f, 1962.62f);
                    path5111Path.lineTo(1637.26f, 1993.56f);
                    path5111Path.cubicTo(1637.26f, 2006.31f, 1639.04f, 2015.81f, 1642.6f, 2022.06f);
                    path5111Path.cubicTo(1646.17f, 2028.31f, 1651.76f, 2031.43f, 1659.38f, 2031.43f);
                    path5111Path.cubicTo(1666.7f, 2031.43f, 1672.1f, 2028.56f, 1675.6f, 2022.81f);
                    path5111Path.cubicTo(1679.1f, 2016.99f, 1680.95f, 2007.93f, 1681.13f, 1995.62f);
                    path5111Path.lineTo(1681.13f, 1964.77f);
                    path5111Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5111Path, paint);

                    // path5113
                    RectF path5113Rect = CacheForCanvas1.path5113Rect;
                    path5113Rect.set(1726.51f, 2023.74f, 1753.04f, 2048.77f);
                    Path path5113Path = CacheForCanvas1.path5113Path;
                    path5113Path.reset();
                    path5113Path.moveTo(1739.73f, 2023.74f);
                    path5113Path.cubicTo(1743.98f, 2023.74f, 1747.26f, 2024.96f, 1749.57f, 2027.4f);
                    path5113Path.cubicTo(1751.88f, 2029.77f, 1753.04f, 2032.77f, 1753.04f, 2036.4f);
                    path5113Path.cubicTo(1753.04f, 2039.96f, 1751.88f, 2042.93f, 1749.57f, 2045.31f);
                    path5113Path.cubicTo(1747.26f, 2047.62f, 1743.98f, 2048.77f, 1739.73f, 2048.77f);
                    path5113Path.cubicTo(1735.67f, 2048.77f, 1732.45f, 2047.62f, 1730.07f, 2045.31f);
                    path5113Path.cubicTo(1727.7f, 2042.99f, 1726.51f, 2040.02f, 1726.51f, 2036.4f);
                    path5113Path.cubicTo(1726.51f, 2032.77f, 1727.67f, 2029.77f, 1729.98f, 2027.4f);
                    path5113Path.cubicTo(1732.29f, 2024.96f, 1735.54f, 2023.74f, 1739.73f, 2023.74f);
                    path5113Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5113Path, paint);

                    // path5115
                    RectF path5115Rect = CacheForCanvas1.path5115Rect;
                    path5115Rect.set(1779.57f, 1911.24f, 1868.92f, 2049.62f);
                    Path path5115Path = CacheForCanvas1.path5115Path;
                    path5115Path.reset();
                    path5115Path.moveTo(1783.79f, 1980.06f);
                    path5115Path.lineTo(1791.2f, 1911.24f);
                    path5115Path.lineTo(1864.51f, 1911.24f);
                    path5115Path.lineTo(1864.51f, 1930.93f);
                    path5115Path.lineTo(1810.13f, 1930.93f);
                    path5115Path.lineTo(1806.38f, 1963.56f);
                    path5115Path.cubicTo(1812.7f, 1959.93f, 1819.82f, 1958.12f, 1827.76f, 1958.12f);
                    path5115Path.cubicTo(1840.76f, 1958.12f, 1850.85f, 1962.27f, 1858.04f, 1970.59f);
                    path5115Path.cubicTo(1865.29f, 1978.9f, 1868.92f, 1990.09f, 1868.92f, 2004.15f);
                    path5115Path.cubicTo(1868.92f, 2018.02f, 1864.92f, 2029.09f, 1856.92f, 2037.34f);
                    path5115Path.cubicTo(1848.92f, 2045.52f, 1837.95f, 2049.62f, 1824.01f, 2049.62f);
                    path5115Path.cubicTo(1811.51f, 2049.62f, 1801.13f, 2046.09f, 1792.88f, 2039.02f);
                    path5115Path.cubicTo(1784.7f, 2031.9f, 1780.26f, 2022.52f, 1779.57f, 2010.9f);
                    path5115Path.lineTo(1801.6f, 2010.9f);
                    path5115Path.cubicTo(1802.48f, 2017.52f, 1804.85f, 2022.62f, 1808.73f, 2026.18f);
                    path5115Path.cubicTo(1812.6f, 2029.68f, 1817.67f, 2031.43f, 1823.92f, 2031.43f);
                    path5115Path.cubicTo(1830.92f, 2031.43f, 1836.35f, 2028.93f, 1840.23f, 2023.93f);
                    path5115Path.cubicTo(1844.17f, 2018.93f, 1846.13f, 2012.12f, 1846.13f, 2003.49f);
                    path5115Path.cubicTo(1846.13f, 1995.18f, 1843.98f, 1988.62f, 1839.67f, 1983.81f);
                    path5115Path.cubicTo(1835.35f, 1978.93f, 1829.42f, 1976.49f, 1821.85f, 1976.49f);
                    path5115Path.cubicTo(1817.73f, 1976.49f, 1814.23f, 1977.06f, 1811.35f, 1978.18f);
                    path5115Path.cubicTo(1808.48f, 1979.24f, 1805.35f, 1981.37f, 1801.98f, 1984.56f);
                    path5115Path.lineTo(1783.79f, 1980.06f);
                    path5115Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5115Path, paint);
                }

                // flowRoot3269-9-4
                {
                    // path5145
                    RectF path5145Rect = CacheForCanvas1.path5145Rect;
                    path5145Rect.set(1612.38f, 161.91f, 1706.79f, 300.29f);
                    Path path5145Path = CacheForCanvas1.path5145Path;
                    path5145Path.reset();
                    path5145Path.moveTo(1706.79f, 300.29f);
                    path5145Path.lineTo(1615.01f, 300.29f);
                    path5145Path.lineTo(1615.01f, 284.63f);
                    path5145Path.lineTo(1660.48f, 235.04f);
                    path5145Path.cubicTo(1667.04f, 227.72f, 1671.7f, 221.63f, 1674.45f, 216.75f);
                    path5145Path.cubicTo(1677.26f, 211.82f, 1678.67f, 206.88f, 1678.67f, 201.94f);
                    path5145Path.cubicTo(1678.67f, 195.44f, 1676.82f, 190.19f, 1673.13f, 186.19f);
                    path5145Path.cubicTo(1669.51f, 182.19f, 1664.6f, 180.19f, 1658.42f, 180.19f);
                    path5145Path.cubicTo(1651.04f, 180.19f, 1645.32f, 182.44f, 1641.26f, 186.94f);
                    path5145Path.cubicTo(1637.2f, 191.44f, 1635.17f, 197.6f, 1635.17f, 205.41f);
                    path5145Path.lineTo(1612.38f, 205.41f);
                    path5145Path.cubicTo(1612.38f, 197.1f, 1614.26f, 189.63f, 1618.01f, 183f);
                    path5145Path.cubicTo(1621.82f, 176.32f, 1627.23f, 171.13f, 1634.23f, 167.44f);
                    path5145Path.cubicTo(1641.29f, 163.75f, 1649.42f, 161.91f, 1658.6f, 161.91f);
                    path5145Path.cubicTo(1671.85f, 161.91f, 1682.29f, 165.25f, 1689.92f, 171.94f);
                    path5145Path.cubicTo(1697.6f, 178.57f, 1701.45f, 187.75f, 1701.45f, 199.5f);
                    path5145Path.cubicTo(1701.45f, 206.32f, 1699.51f, 213.47f, 1695.63f, 220.97f);
                    path5145Path.cubicTo(1691.82f, 228.41f, 1685.54f, 236.88f, 1676.79f, 246.38f);
                    path5145Path.lineTo(1643.42f, 282.1f);
                    path5145Path.lineTo(1706.79f, 282.1f);
                    path5145Path.lineTo(1706.79f, 300.29f);
                    path5145Path.close();

                    paint.reset();
                    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fillColor6);
                    canvas.drawPath(path5145Path, paint);
                }
            }
        }

        canvas.restore();
    }

    private static void drawLayer1(Paint paint, Canvas canvas) {
        {
            int fillColor = Color.argb(49, 0, 0, 0);
            int strokeColor = Color.argb(255, 0, 0, 0);
            int fillColor6 = Color.argb(255, 0, 0, 0);
            int fillColor2 = Color.argb(255, 206, 30, 19);
            int fillColor3 = Color.argb(255, 152, 191, 36);
            int fillColor5 = Color.argb(255, 0, 128, 204);
            int fillColor4 = Color.argb(255, 255, 255, 255);

            // path4703
            RectF path4703Rect = CacheForCanvas1.path4703Rect;
            path4703Rect.set(174.44f, 1684.48f, 956.79f, 2195.43f);
            Path path4703Path = CacheForCanvas1.path4703Path;
            path4703Path.reset();
            path4703Path.moveTo(174.44f, 1684.48f);
            path4703Path.lineTo(578.58f, 1684.48f);
            path4703Path.lineTo(668.58f, 1840.36f);
            path4703Path.lineTo(841.79f, 1840.36f);
            path4703Path.lineTo(861.79f, 1875f);
            path4703Path.lineTo(688.58f, 1875f);
            path4703Path.lineTo(763.58f, 2004.91f);
            path4703Path.lineTo(936.79f, 2004.91f);
            path4703Path.lineTo(956.79f, 2039.55f);
            path4703Path.lineTo(783.58f, 2039.55f);
            path4703Path.lineTo(873.58f, 2195.43f);
            path4703Path.lineTo(469.44f, 2195.43f);
            path4703Path.lineTo(174.44f, 1684.48f);
            path4703Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            path4703Path.setFillType(Path.FillType.EVEN_ODD);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor);
            canvas.drawPath(path4703Path, paint);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.07f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path4703Path, paint);
            canvas.restore();

            // path4701
            RectF path4701Rect = CacheForCanvas1.path4701Rect;
            path4701Rect.set(29.69f, 1124.48f, 677.29f, 1635.43f);
            Path path4701Path = CacheForCanvas1.path4701Path;
            path4701Path.reset();
            path4701Path.moveTo(29.69f, 1193.76f);
            path4701Path.lineTo(214.44f, 1193.76f);
            path4701Path.lineTo(174.44f, 1124.48f);
            path4701Path.lineTo(382.29f, 1124.48f);
            path4701Path.lineTo(677.29f, 1635.43f);
            path4701Path.lineTo(469.44f, 1635.43f);
            path4701Path.lineTo(429.44f, 1566.15f);
            path4701Path.lineTo(244.69f, 1566.15f);
            path4701Path.lineTo(29.69f, 1193.76f);
            path4701Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            path4701Path.setFillType(Path.FillType.EVEN_ODD);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor);
            canvas.drawPath(path4701Path, paint);

            // path4699
            RectF path4699Rect = CacheForCanvas1.path4699Rect;
            path4699Rect.set(668.58f, 625.1f, 1519.86f, 1014.81f);
            Path path4699Path = CacheForCanvas1.path4699Path;
            path4699Path.reset();
            path4699Path.moveTo(668.58f, 720.36f);
            path4699Path.lineTo(688.58f, 755f);
            path4699Path.lineTo(861.79f, 755f);
            path4699Path.lineTo(936.79f, 884.91f);
            path4699Path.lineTo(763.58f, 884.91f);
            path4699Path.lineTo(783.58f, 919.55f);
            path4699Path.lineTo(956.79f, 919.55f);
            path4699Path.lineTo(1011.79f, 1014.81f);
            path4699Path.lineTo(1519.86f, 1014.81f);
            path4699Path.lineTo(1499.86f, 980.17f);
            path4699Path.lineTo(1037.98f, 980.17f);
            path4699Path.lineTo(852.98f, 659.74f);
            path4699Path.lineTo(1314.86f, 659.74f);
            path4699Path.lineTo(1294.86f, 625.1f);
            path4699Path.lineTo(786.79f, 625.1f);
            path4699Path.lineTo(841.79f, 720.36f);
            path4699Path.lineTo(668.58f, 720.36f);
            path4699Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            path4699Path.setFillType(Path.FillType.EVEN_ODD);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor);
            canvas.drawPath(path4699Path, paint);

            // path4697
            RectF path4697Rect = CacheForCanvas1.path4697Rect;
            path4697Rect.set(174.44f, 564.48f, 677.29f, 1075.43f);
            Path path4697Path = CacheForCanvas1.path4697Path;
            path4697Path.reset();
            path4697Path.moveTo(174.44f, 564.48f);
            path4697Path.lineTo(382.29f, 564.48f);
            path4697Path.lineTo(677.29f, 1075.43f);
            path4697Path.lineTo(469.44f, 1075.43f);
            path4697Path.lineTo(174.44f, 564.48f);
            path4697Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            path4697Path.setFillType(Path.FillType.EVEN_ODD);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor);
            canvas.drawPath(path4697Path, paint);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.07f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path4697Path, paint);
            canvas.restore();

            // path4680
            RectF path4680Rect = CacheForCanvas1.path4680Rect;
            path4680Rect.set(233.93f, 104.19f, 676.78f, 511.22f);
            Path path4680Path = CacheForCanvas1.path4680Path;
            path4680Path.reset();
            path4680Path.moveTo(233.93f, 104.19f);
            path4680Path.lineTo(441.78f, 104.19f);
            path4680Path.lineTo(676.78f, 511.22f);
            path4680Path.lineTo(468.93f, 511.22f);
            path4680Path.lineTo(233.93f, 104.19f);
            path4680Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            path4680Path.setFillType(Path.FillType.EVEN_ODD);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor);
            canvas.drawPath(path4680Path, paint);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.07f);
            paint.setStrokeMiter(3.9f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path4680Path, paint);
            canvas.restore();

            // rect1521
            RectF rect1521Rect = CacheForCanvas1.rect1521Rect;
            rect1521Rect.set(417.88f, 586.13f, 580.49f, 607.78f);
            Path rect1521Path = CacheForCanvas1.rect1521Path;
            rect1521Path.reset();
            rect1521Path.moveTo(417.88f, 586.13f);
            rect1521Path.lineTo(567.99f, 586.13f);
            rect1521Path.lineTo(580.49f, 607.78f);
            rect1521Path.lineTo(430.38f, 607.78f);
            rect1521Path.lineTo(417.88f, 586.13f);
            rect1521Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor2);
            canvas.drawPath(rect1521Path, paint);

            // rect4667
            RectF rect4667Rect = CacheForCanvas1.rect4667Rect;
            rect4667Rect.set(382.29f, 564.48f, 873.58f, 1075.43f);
            Path rect4667Path = CacheForCanvas1.rect4667Path;
            rect4667Path.reset();
            rect4667Path.moveTo(382.29f, 564.48f);
            rect4667Path.lineTo(578.58f, 564.48f);
            rect4667Path.lineTo(873.58f, 1075.43f);
            rect4667Path.lineTo(677.29f, 1075.43f);
            rect4667Path.lineTo(382.29f, 564.48f);
            rect4667Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            rect4667Path.setFillType(Path.FillType.EVEN_ODD);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor3);
            canvas.drawPath(rect4667Path, paint);

            // rect4665
            RectF rect4665Rect = CacheForCanvas1.rect4665Rect;
            rect4665Rect.set(29.69f, 633.76f, 429.44f, 1006.15f);
            Path rect4665Path = CacheForCanvas1.rect4665Path;
            rect4665Path.reset();
            rect4665Path.moveTo(29.69f, 633.76f);
            rect4665Path.lineTo(214.44f, 633.76f);
            rect4665Path.lineTo(429.44f, 1006.15f);
            rect4665Path.lineTo(244.69f, 1006.15f);
            rect4665Path.lineTo(29.69f, 633.76f);
            rect4665Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            rect4665Path.setFillType(Path.FillType.EVEN_ODD);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor3);
            canvas.drawPath(rect4665Path, paint);

            // path2176
            RectF path2176Rect = CacheForCanvas1.path2176Rect;
            path2176Rect.set(373.93f, 346.67f, 393.93f, 381.31f);
            Path path2176Path = CacheForCanvas1.path2176Path;
            path2176Path.reset();
            path2176Path.moveTo(393.93f, 381.31f);
            path2176Path.lineTo(373.93f, 346.67f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path2176Path, paint);
            canvas.restore();

            // path2180
            RectF path2180Rect = CacheForCanvas1.path2180Rect;
            path2180Rect.set(233.93f, 104.19f, 268.93f, 164.81f);
            Path path2180Path = CacheForCanvas1.path2180Path;
            path2180Path.reset();
            path2180Path.moveTo(233.93f, 104.19f);
            path2180Path.lineTo(268.93f, 164.81f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path2180Path, paint);
            canvas.restore();

            // path828
            RectF path828Rect = CacheForCanvas1.path828Rect;
            path828Rect.set(29.69f, 564.48f, 873.58f, 1075.43f);
            Path path828Path = CacheForCanvas1.path828Path;
            path828Path.reset();
            path828Path.moveTo(688.58f, 755f);
            path828Path.lineTo(763.58f, 884.91f);
            path828Path.moveTo(783.53f, 919.46f);
            path828Path.lineTo(873.58f, 1075.43f);
            path828Path.lineTo(683.15f, 1075.43f);
            path828Path.lineTo(469.44f, 1075.43f);
            path828Path.lineTo(429.44f, 1006.15f);
            path828Path.lineTo(244.69f, 1006.15f);
            path828Path.lineTo(29.69f, 633.76f);
            path828Path.lineTo(214.44f, 633.76f);
            path828Path.lineTo(174.44f, 564.48f);
            path828Path.lineTo(578.58f, 564.48f);
            path828Path.lineTo(668.58f, 720.36f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path828Path, paint);
            canvas.restore();

            // path830
            RectF path830Rect = CacheForCanvas1.path830Rect;
            path830Rect.set(394.44f, 945.53f, 429.44f, 1006.15f);
            Path path830Path = CacheForCanvas1.path830Path;
            path830Path.reset();
            path830Path.moveTo(394.44f, 945.53f);
            path830Path.lineTo(429.44f, 1006.15f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path830Path, paint);
            canvas.restore();

            // path832
            RectF path832Rect = CacheForCanvas1.path832Rect;
            path832Rect.set(351.35f, 876.25f, 374.44f, 910.89f);
            Path path832Path = CacheForCanvas1.path832Path;
            path832Path.reset();
            path832Path.moveTo(354.44f, 876.25f);
            path832Path.lineTo(374.44f, 910.89f);
            path832Path.lineTo(351.35f, 910.89f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path832Path, paint);
            canvas.restore();

            // path834
            RectF path834Rect = CacheForCanvas1.path834Rect;
            path834Rect.set(189.69f, 910.89f, 212.78f, 910.89f);
            Path path834Path = CacheForCanvas1.path834Path;
            path834Path.reset();
            path834Path.moveTo(189.69f, 910.89f);
            path834Path.lineTo(212.78f, 910.89f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path834Path, paint);
            canvas.restore();

            // path836
            RectF path836Rect = CacheForCanvas1.path836Rect;
            path836Rect.set(309.44f, 798.3f, 334.44f, 841.6f);
            Path path836Path = CacheForCanvas1.path836Path;
            path836Path.reset();
            path836Path.moveTo(334.44f, 841.6f);
            path836Path.lineTo(309.44f, 798.3f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path836Path, paint);
            canvas.restore();

            // path838
            RectF path838Rect = CacheForCanvas1.path838Rect;
            path838Rect.set(246.35f, 729.02f, 289.44f, 763.66f);
            Path path838Path = CacheForCanvas1.path838Path;
            path838Path.reset();
            path838Path.moveTo(246.35f, 729.02f);
            path838Path.lineTo(269.44f, 729.02f);
            path838Path.lineTo(289.44f, 763.66f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path838Path, paint);
            canvas.restore();

            // path840
            RectF path840Rect = CacheForCanvas1.path840Rect;
            path840Rect.set(84.69f, 729.02f, 107.78f, 729.02f);
            Path path840Path = CacheForCanvas1.path840Path;
            path840Path.reset();
            path840Path.moveTo(107.78f, 729.02f);
            path840Path.lineTo(84.69f, 729.02f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path840Path, paint);
            canvas.restore();

            // path842
            RectF path842Rect = CacheForCanvas1.path842Rect;
            path842Rect.set(214.44f, 633.76f, 249.44f, 694.38f);
            Path path842Path = CacheForCanvas1.path842Path;
            path842Path.reset();
            path842Path.moveTo(249.44f, 694.38f);
            path842Path.lineTo(214.44f, 633.76f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path842Path, paint);
            canvas.restore();

            // path844
            RectF path844Rect = CacheForCanvas1.path844Rect;
            path844Rect.set(382.29f, 564.48f, 492.29f, 755f);
            Path path844Path = CacheForCanvas1.path844Path;
            path844Path.reset();
            path844Path.moveTo(492.29f, 755f);
            path844Path.lineTo(382.29f, 564.48f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path844Path, paint);
            canvas.restore();

            // path846
            RectF path846Rect = CacheForCanvas1.path846Rect;
            path846Rect.set(567.29f, 884.91f, 677.29f, 1075.43f);
            Path path846Path = CacheForCanvas1.path846Path;
            path846Path.reset();
            path846Path.moveTo(567.29f, 884.91f);
            path846Path.lineTo(677.29f, 1075.43f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path846Path, paint);
            canvas.restore();

            // path876
            RectF path876Rect = CacheForCanvas1.path876Rect;
            path876Rect.set(401.94f, 958.52f, 459.67f, 958.52f);
            Path path876Path = CacheForCanvas1.path876Path;
            path876Path.reset();
            path876Path.moveTo(459.67f, 958.52f);
            path876Path.lineTo(401.94f, 958.52f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876Path, paint);
            canvas.restore();

            // path876-3
            RectF path8763Rect = CacheForCanvas1.path8763Rect;
            path8763Rect.set(404.44f, 962.85f, 462.17f, 962.85f);
            Path path8763Path = CacheForCanvas1.path8763Path;
            path8763Path.reset();
            path8763Path.moveTo(462.17f, 962.85f);
            path8763Path.lineTo(404.44f, 962.85f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763Path, paint);
            canvas.restore();

            // path876-6
            RectF path8766Rect = CacheForCanvas1.path8766Rect;
            path8766Rect.set(406.94f, 967.18f, 464.67f, 967.18f);
            Path path8766Path = CacheForCanvas1.path8766Path;
            path8766Path.reset();
            path8766Path.moveTo(464.67f, 967.18f);
            path8766Path.lineTo(406.94f, 967.18f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766Path, paint);
            canvas.restore();

            // path876-7
            RectF path8767Rect = CacheForCanvas1.path8767Rect;
            path8767Rect.set(409.44f, 971.51f, 467.17f, 971.51f);
            Path path8767Path = CacheForCanvas1.path8767Path;
            path8767Path.reset();
            path8767Path.moveTo(467.17f, 971.51f);
            path8767Path.lineTo(409.44f, 971.51f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767Path, paint);
            canvas.restore();

            // path876-5
            RectF path8765Rect = CacheForCanvas1.path8765Rect;
            path8765Rect.set(399.44f, 954.19f, 457.17f, 954.19f);
            Path path8765Path = CacheForCanvas1.path8765Path;
            path8765Path.reset();
            path8765Path.moveTo(457.17f, 954.19f);
            path8765Path.lineTo(399.44f, 954.19f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765Path, paint);
            canvas.restore();

            // path876-70
            RectF path87670Rect = CacheForCanvas1.path87670Rect;
            path87670Rect.set(414.44f, 980.17f, 472.17f, 980.17f);
            Path path87670Path = CacheForCanvas1.path87670Path;
            path87670Path.reset();
            path87670Path.moveTo(472.17f, 980.17f);
            path87670Path.lineTo(414.44f, 980.17f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87670Path, paint);
            canvas.restore();

            // path876-3-9
            RectF path87639Rect = CacheForCanvas1.path87639Rect;
            path87639Rect.set(416.94f, 984.5f, 474.67f, 984.5f);
            Path path87639Path = CacheForCanvas1.path87639Path;
            path87639Path.reset();
            path87639Path.moveTo(474.67f, 984.5f);
            path87639Path.lineTo(416.94f, 984.5f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87639Path, paint);
            canvas.restore();

            // path876-6-3
            RectF path87663Rect = CacheForCanvas1.path87663Rect;
            path87663Rect.set(419.44f, 988.83f, 477.17f, 988.83f);
            Path path87663Path = CacheForCanvas1.path87663Path;
            path87663Path.reset();
            path87663Path.moveTo(477.17f, 988.83f);
            path87663Path.lineTo(419.44f, 988.83f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87663Path, paint);
            canvas.restore();

            // path876-7-6
            RectF path87676Rect = CacheForCanvas1.path87676Rect;
            path87676Rect.set(421.94f, 993.16f, 479.67f, 993.16f);
            Path path87676Path = CacheForCanvas1.path87676Path;
            path87676Path.reset();
            path87676Path.moveTo(479.67f, 993.16f);
            path87676Path.lineTo(421.94f, 993.16f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87676Path, paint);
            canvas.restore();

            // path876-5-0
            RectF path87650Rect = CacheForCanvas1.path87650Rect;
            path87650Rect.set(411.94f, 975.84f, 469.67f, 975.84f);
            Path path87650Path = CacheForCanvas1.path87650Path;
            path87650Path.reset();
            path87650Path.moveTo(469.67f, 975.84f);
            path87650Path.lineTo(411.94f, 975.84f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87650Path, paint);
            canvas.restore();

            // path876-62
            RectF path87662Rect = CacheForCanvas1.path87662Rect;
            path87662Rect.set(426.94f, 1001.82f, 484.67f, 1001.82f);
            Path path87662Path = CacheForCanvas1.path87662Path;
            path87662Path.reset();
            path87662Path.moveTo(484.67f, 1001.82f);
            path87662Path.lineTo(426.94f, 1001.82f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87662Path, paint);
            canvas.restore();

            // path876-3-6
            RectF path87636Rect = CacheForCanvas1.path87636Rect;
            path87636Rect.set(429.44f, 1006.15f, 487.17f, 1006.15f);
            Path path87636Path = CacheForCanvas1.path87636Path;
            path87636Path.reset();
            path87636Path.moveTo(487.17f, 1006.15f);
            path87636Path.lineTo(429.44f, 1006.15f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87636Path, paint);
            canvas.restore();

            // path876-6-1
            RectF path87661Rect = CacheForCanvas1.path87661Rect;
            path87661Rect.set(431.94f, 1010.48f, 489.67f, 1010.48f);
            Path path87661Path = CacheForCanvas1.path87661Path;
            path87661Path.reset();
            path87661Path.moveTo(489.67f, 1010.48f);
            path87661Path.lineTo(431.94f, 1010.48f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87661Path, paint);
            canvas.restore();

            // path876-7-8
            RectF path87678Rect = CacheForCanvas1.path87678Rect;
            path87678Rect.set(434.44f, 1014.81f, 492.17f, 1014.81f);
            Path path87678Path = CacheForCanvas1.path87678Path;
            path87678Path.reset();
            path87678Path.moveTo(492.17f, 1014.81f);
            path87678Path.lineTo(434.44f, 1014.81f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87678Path, paint);
            canvas.restore();

            // path876-5-7
            RectF path87657Rect = CacheForCanvas1.path87657Rect;
            path87657Rect.set(424.44f, 997.49f, 482.17f, 997.49f);
            Path path87657Path = CacheForCanvas1.path87657Path;
            path87657Path.reset();
            path87657Path.moveTo(482.17f, 997.49f);
            path87657Path.lineTo(424.44f, 997.49f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87657Path, paint);
            canvas.restore();

            // path876-70-9
            RectF path876709Rect = CacheForCanvas1.path876709Rect;
            path876709Rect.set(439.44f, 1023.47f, 497.17f, 1023.47f);
            Path path876709Path = CacheForCanvas1.path876709Path;
            path876709Path.reset();
            path876709Path.moveTo(497.17f, 1023.47f);
            path876709Path.lineTo(439.44f, 1023.47f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876709Path, paint);
            canvas.restore();

            // path876-3-9-2
            RectF path876392Rect = CacheForCanvas1.path876392Rect;
            path876392Rect.set(441.94f, 1027.8f, 499.67f, 1027.8f);
            Path path876392Path = CacheForCanvas1.path876392Path;
            path876392Path.reset();
            path876392Path.moveTo(499.67f, 1027.8f);
            path876392Path.lineTo(441.94f, 1027.8f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876392Path, paint);
            canvas.restore();

            // path876-6-3-0
            RectF path876630Rect = CacheForCanvas1.path876630Rect;
            path876630Rect.set(444.44f, 1032.13f, 502.17f, 1032.13f);
            Path path876630Path = CacheForCanvas1.path876630Path;
            path876630Path.reset();
            path876630Path.moveTo(502.17f, 1032.13f);
            path876630Path.lineTo(444.44f, 1032.13f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876630Path, paint);
            canvas.restore();

            // path876-7-6-2
            RectF path876762Rect = CacheForCanvas1.path876762Rect;
            path876762Rect.set(446.94f, 1036.46f, 504.67f, 1036.46f);
            Path path876762Path = CacheForCanvas1.path876762Path;
            path876762Path.reset();
            path876762Path.moveTo(504.67f, 1036.46f);
            path876762Path.lineTo(446.94f, 1036.46f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876762Path, paint);
            canvas.restore();

            // path876-5-0-3
            RectF path876503Rect = CacheForCanvas1.path876503Rect;
            path876503Rect.set(436.94f, 1019.14f, 494.67f, 1019.14f);
            Path path876503Path = CacheForCanvas1.path876503Path;
            path876503Path.reset();
            path876503Path.moveTo(494.67f, 1019.14f);
            path876503Path.lineTo(436.94f, 1019.14f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876503Path, paint);
            canvas.restore();

            // rect1196
            RectF rect1196Rect = CacheForCanvas1.rect1196Rect;
            rect1196Rect.set(457.17f, 954.19f, 510.45f, 1036.46f);
            Path rect1196Path = CacheForCanvas1.rect1196Path;
            rect1196Path.reset();
            rect1196Path.moveTo(457.17f, 954.19f);
            rect1196Path.lineTo(462.95f, 954.19f);
            rect1196Path.lineTo(510.45f, 1036.46f);
            rect1196Path.lineTo(504.67f, 1036.46f);
            rect1196Path.lineTo(457.17f, 954.19f);
            rect1196Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor4);
            canvas.drawPath(rect1196Path, paint);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.07f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(rect1196Path, paint);
            canvas.restore();

            // path876-30
            RectF path87630Rect = CacheForCanvas1.path87630Rect;
            path87630Rect.set(552.05f, 958.52f, 609.79f, 958.52f);
            Path path87630Path = CacheForCanvas1.path87630Path;
            path87630Path.reset();
            path87630Path.moveTo(552.05f, 958.52f);
            path87630Path.lineTo(609.79f, 958.52f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87630Path, paint);
            canvas.restore();

            // path876-3-7
            RectF path87637Rect = CacheForCanvas1.path87637Rect;
            path87637Rect.set(554.55f, 962.85f, 612.29f, 962.85f);
            Path path87637Path = CacheForCanvas1.path87637Path;
            path87637Path.reset();
            path87637Path.moveTo(554.55f, 962.85f);
            path87637Path.lineTo(612.29f, 962.85f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87637Path, paint);
            canvas.restore();

            // path876-6-8
            RectF path87668Rect = CacheForCanvas1.path87668Rect;
            path87668Rect.set(557.05f, 967.18f, 614.79f, 967.18f);
            Path path87668Path = CacheForCanvas1.path87668Path;
            path87668Path.reset();
            path87668Path.moveTo(557.05f, 967.18f);
            path87668Path.lineTo(614.79f, 967.18f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87668Path, paint);
            canvas.restore();

            // path876-7-68
            RectF path876768Rect = CacheForCanvas1.path876768Rect;
            path876768Rect.set(559.55f, 971.51f, 617.29f, 971.51f);
            Path path876768Path = CacheForCanvas1.path876768Path;
            path876768Path.reset();
            path876768Path.moveTo(559.55f, 971.51f);
            path876768Path.lineTo(617.29f, 971.51f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876768Path, paint);
            canvas.restore();

            // path876-5-8
            RectF path87658Rect = CacheForCanvas1.path87658Rect;
            path87658Rect.set(549.55f, 954.19f, 607.29f, 954.19f);
            Path path87658Path = CacheForCanvas1.path87658Path;
            path87658Path.reset();
            path87658Path.moveTo(549.55f, 954.19f);
            path87658Path.lineTo(607.29f, 954.19f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87658Path, paint);
            canvas.restore();

            // path876-70-4
            RectF path876704Rect = CacheForCanvas1.path876704Rect;
            path876704Rect.set(564.55f, 980.17f, 622.29f, 980.17f);
            Path path876704Path = CacheForCanvas1.path876704Path;
            path876704Path.reset();
            path876704Path.moveTo(564.55f, 980.17f);
            path876704Path.lineTo(622.29f, 980.17f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876704Path, paint);
            canvas.restore();

            // path876-3-9-3
            RectF path876393Rect = CacheForCanvas1.path876393Rect;
            path876393Rect.set(567.05f, 984.5f, 624.79f, 984.5f);
            Path path876393Path = CacheForCanvas1.path876393Path;
            path876393Path.reset();
            path876393Path.moveTo(567.05f, 984.5f);
            path876393Path.lineTo(624.79f, 984.5f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876393Path, paint);
            canvas.restore();

            // path876-6-3-1
            RectF path876631Rect = CacheForCanvas1.path876631Rect;
            path876631Rect.set(569.55f, 988.83f, 627.29f, 988.83f);
            Path path876631Path = CacheForCanvas1.path876631Path;
            path876631Path.reset();
            path876631Path.moveTo(569.55f, 988.83f);
            path876631Path.lineTo(627.29f, 988.83f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876631Path, paint);
            canvas.restore();

            // path876-7-6-4
            RectF path876764Rect = CacheForCanvas1.path876764Rect;
            path876764Rect.set(572.05f, 993.16f, 629.79f, 993.16f);
            Path path876764Path = CacheForCanvas1.path876764Path;
            path876764Path.reset();
            path876764Path.moveTo(572.05f, 993.16f);
            path876764Path.lineTo(629.79f, 993.16f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876764Path, paint);
            canvas.restore();

            // path876-5-0-9
            RectF path876509Rect = CacheForCanvas1.path876509Rect;
            path876509Rect.set(562.05f, 975.84f, 619.79f, 975.84f);
            Path path876509Path = CacheForCanvas1.path876509Path;
            path876509Path.reset();
            path876509Path.moveTo(562.05f, 975.84f);
            path876509Path.lineTo(619.79f, 975.84f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876509Path, paint);
            canvas.restore();

            // path876-62-2
            RectF path876622Rect = CacheForCanvas1.path876622Rect;
            path876622Rect.set(577.05f, 1001.82f, 634.79f, 1001.82f);
            Path path876622Path = CacheForCanvas1.path876622Path;
            path876622Path.reset();
            path876622Path.moveTo(577.05f, 1001.82f);
            path876622Path.lineTo(634.79f, 1001.82f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876622Path, paint);
            canvas.restore();

            // path876-3-6-0
            RectF path876360Rect = CacheForCanvas1.path876360Rect;
            path876360Rect.set(579.55f, 1006.15f, 637.29f, 1006.15f);
            Path path876360Path = CacheForCanvas1.path876360Path;
            path876360Path.reset();
            path876360Path.moveTo(579.55f, 1006.15f);
            path876360Path.lineTo(637.29f, 1006.15f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876360Path, paint);
            canvas.restore();

            // path876-6-1-6
            RectF path876616Rect = CacheForCanvas1.path876616Rect;
            path876616Rect.set(582.05f, 1010.48f, 639.79f, 1010.48f);
            Path path876616Path = CacheForCanvas1.path876616Path;
            path876616Path.reset();
            path876616Path.moveTo(582.05f, 1010.48f);
            path876616Path.lineTo(639.79f, 1010.48f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876616Path, paint);
            canvas.restore();

            // path876-7-8-8
            RectF path876788Rect = CacheForCanvas1.path876788Rect;
            path876788Rect.set(584.55f, 1014.81f, 642.29f, 1014.81f);
            Path path876788Path = CacheForCanvas1.path876788Path;
            path876788Path.reset();
            path876788Path.moveTo(584.55f, 1014.81f);
            path876788Path.lineTo(642.29f, 1014.81f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876788Path, paint);
            canvas.restore();

            // path876-5-7-9
            RectF path876579Rect = CacheForCanvas1.path876579Rect;
            path876579Rect.set(574.55f, 997.49f, 632.29f, 997.49f);
            Path path876579Path = CacheForCanvas1.path876579Path;
            path876579Path.reset();
            path876579Path.moveTo(574.55f, 997.49f);
            path876579Path.lineTo(632.29f, 997.49f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876579Path, paint);
            canvas.restore();

            // path876-70-9-2
            RectF path8767092Rect = CacheForCanvas1.path8767092Rect;
            path8767092Rect.set(589.55f, 1023.47f, 647.29f, 1023.47f);
            Path path8767092Path = CacheForCanvas1.path8767092Path;
            path8767092Path.reset();
            path8767092Path.moveTo(589.55f, 1023.47f);
            path8767092Path.lineTo(647.29f, 1023.47f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767092Path, paint);
            canvas.restore();

            // path876-3-9-2-6
            RectF path8763926Rect = CacheForCanvas1.path8763926Rect;
            path8763926Rect.set(592.05f, 1027.8f, 649.79f, 1027.8f);
            Path path8763926Path = CacheForCanvas1.path8763926Path;
            path8763926Path.reset();
            path8763926Path.moveTo(592.05f, 1027.8f);
            path8763926Path.lineTo(649.79f, 1027.8f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763926Path, paint);
            canvas.restore();

            // path876-6-3-0-6
            RectF path8766306Rect = CacheForCanvas1.path8766306Rect;
            path8766306Rect.set(594.55f, 1032.13f, 652.29f, 1032.13f);
            Path path8766306Path = CacheForCanvas1.path8766306Path;
            path8766306Path.reset();
            path8766306Path.moveTo(594.55f, 1032.13f);
            path8766306Path.lineTo(652.29f, 1032.13f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766306Path, paint);
            canvas.restore();

            // path876-7-6-2-4
            RectF path8767624Rect = CacheForCanvas1.path8767624Rect;
            path8767624Rect.set(597.05f, 1036.46f, 654.79f, 1036.46f);
            Path path8767624Path = CacheForCanvas1.path8767624Path;
            path8767624Path.reset();
            path8767624Path.moveTo(597.05f, 1036.46f);
            path8767624Path.lineTo(654.79f, 1036.46f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767624Path, paint);
            canvas.restore();

            // path876-5-0-3-9
            RectF path8765039Rect = CacheForCanvas1.path8765039Rect;
            path8765039Rect.set(587.05f, 1019.14f, 644.79f, 1019.14f);
            Path path8765039Path = CacheForCanvas1.path8765039Path;
            path8765039Path.reset();
            path8765039Path.moveTo(587.05f, 1019.14f);
            path8765039Path.lineTo(644.79f, 1019.14f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765039Path, paint);
            canvas.restore();

            // rect1196-5
            RectF rect11965Rect = CacheForCanvas1.rect11965Rect;
            rect11965Rect.set(543.78f, 954.19f, 597.05f, 1036.46f);
            Path rect11965Path = CacheForCanvas1.rect11965Path;
            rect11965Path.reset();
            rect11965Path.moveTo(549.55f, 954.19f);
            rect11965Path.lineTo(543.78f, 954.19f);
            rect11965Path.lineTo(591.28f, 1036.46f);
            rect11965Path.lineTo(597.05f, 1036.46f);
            rect11965Path.lineTo(549.55f, 954.19f);
            rect11965Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor4);
            canvas.drawPath(rect11965Path, paint);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.07f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(rect11965Path, paint);
            canvas.restore();

            // path876-1
            RectF path8761Rect = CacheForCanvas1.path8761Rect;
            path8761Rect.set(465.45f, 958.52f, 546.28f, 958.52f);
            Path path8761Path = CacheForCanvas1.path8761Path;
            path8761Path.reset();
            path8761Path.moveTo(546.28f, 958.52f);
            path8761Path.lineTo(465.45f, 958.52f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8761Path, paint);
            canvas.restore();

            // path876-3-1
            RectF path87631Rect = CacheForCanvas1.path87631Rect;
            path87631Rect.set(467.95f, 962.85f, 548.78f, 962.85f);
            Path path87631Path = CacheForCanvas1.path87631Path;
            path87631Path.reset();
            path87631Path.moveTo(548.78f, 962.85f);
            path87631Path.lineTo(467.95f, 962.85f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87631Path, paint);
            canvas.restore();

            // path876-6-5
            RectF path87665Rect = CacheForCanvas1.path87665Rect;
            path87665Rect.set(470.45f, 967.18f, 551.28f, 967.18f);
            Path path87665Path = CacheForCanvas1.path87665Path;
            path87665Path.reset();
            path87665Path.moveTo(551.28f, 967.18f);
            path87665Path.lineTo(470.45f, 967.18f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87665Path, paint);
            canvas.restore();

            // path876-7-9
            RectF path87679Rect = CacheForCanvas1.path87679Rect;
            path87679Rect.set(472.95f, 971.51f, 553.78f, 971.51f);
            Path path87679Path = CacheForCanvas1.path87679Path;
            path87679Path.reset();
            path87679Path.moveTo(553.78f, 971.51f);
            path87679Path.lineTo(472.95f, 971.51f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87679Path, paint);
            canvas.restore();

            // path876-5-77
            RectF path876577Rect = CacheForCanvas1.path876577Rect;
            path876577Rect.set(462.95f, 954.19f, 543.78f, 954.19f);
            Path path876577Path = CacheForCanvas1.path876577Path;
            path876577Path.reset();
            path876577Path.moveTo(543.78f, 954.19f);
            path876577Path.lineTo(462.95f, 954.19f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876577Path, paint);
            canvas.restore();

            // path876-70-6
            RectF path876706Rect = CacheForCanvas1.path876706Rect;
            path876706Rect.set(477.95f, 980.17f, 558.78f, 980.17f);
            Path path876706Path = CacheForCanvas1.path876706Path;
            path876706Path.reset();
            path876706Path.moveTo(558.78f, 980.17f);
            path876706Path.lineTo(477.95f, 980.17f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876706Path, paint);
            canvas.restore();

            // path876-3-9-7
            RectF path876397Rect = CacheForCanvas1.path876397Rect;
            path876397Rect.set(480.45f, 984.5f, 561.28f, 984.5f);
            Path path876397Path = CacheForCanvas1.path876397Path;
            path876397Path.reset();
            path876397Path.moveTo(561.28f, 984.5f);
            path876397Path.lineTo(480.45f, 984.5f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876397Path, paint);
            canvas.restore();

            // path876-6-3-3
            RectF path876633Rect = CacheForCanvas1.path876633Rect;
            path876633Rect.set(482.95f, 988.83f, 563.78f, 988.83f);
            Path path876633Path = CacheForCanvas1.path876633Path;
            path876633Path.reset();
            path876633Path.moveTo(563.78f, 988.83f);
            path876633Path.lineTo(482.95f, 988.83f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876633Path, paint);
            canvas.restore();

            // path876-7-6-6
            RectF path876766Rect = CacheForCanvas1.path876766Rect;
            path876766Rect.set(485.45f, 993.16f, 566.28f, 993.16f);
            Path path876766Path = CacheForCanvas1.path876766Path;
            path876766Path.reset();
            path876766Path.moveTo(566.28f, 993.16f);
            path876766Path.lineTo(485.45f, 993.16f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876766Path, paint);
            canvas.restore();

            // path876-5-0-5
            RectF path876505Rect = CacheForCanvas1.path876505Rect;
            path876505Rect.set(475.45f, 975.84f, 556.28f, 975.84f);
            Path path876505Path = CacheForCanvas1.path876505Path;
            path876505Path.reset();
            path876505Path.moveTo(556.28f, 975.84f);
            path876505Path.lineTo(475.45f, 975.84f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876505Path, paint);
            canvas.restore();

            // path876-62-6
            RectF path876626Rect = CacheForCanvas1.path876626Rect;
            path876626Rect.set(490.45f, 1001.82f, 571.28f, 1001.82f);
            Path path876626Path = CacheForCanvas1.path876626Path;
            path876626Path.reset();
            path876626Path.moveTo(571.28f, 1001.82f);
            path876626Path.lineTo(490.45f, 1001.82f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876626Path, paint);
            canvas.restore();

            // path876-3-6-3
            RectF path876363Rect = CacheForCanvas1.path876363Rect;
            path876363Rect.set(492.95f, 1006.15f, 573.78f, 1006.15f);
            Path path876363Path = CacheForCanvas1.path876363Path;
            path876363Path.reset();
            path876363Path.moveTo(573.78f, 1006.15f);
            path876363Path.lineTo(492.95f, 1006.15f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876363Path, paint);
            canvas.restore();

            // path876-6-1-9
            RectF path876619Rect = CacheForCanvas1.path876619Rect;
            path876619Rect.set(495.45f, 1010.48f, 576.28f, 1010.48f);
            Path path876619Path = CacheForCanvas1.path876619Path;
            path876619Path.reset();
            path876619Path.moveTo(576.28f, 1010.48f);
            path876619Path.lineTo(495.45f, 1010.48f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876619Path, paint);
            canvas.restore();

            // path876-7-8-4
            RectF path876784Rect = CacheForCanvas1.path876784Rect;
            path876784Rect.set(497.95f, 1014.81f, 578.78f, 1014.81f);
            Path path876784Path = CacheForCanvas1.path876784Path;
            path876784Path.reset();
            path876784Path.moveTo(578.78f, 1014.81f);
            path876784Path.lineTo(497.95f, 1014.81f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876784Path, paint);
            canvas.restore();

            // path876-5-7-8
            RectF path876578Rect = CacheForCanvas1.path876578Rect;
            path876578Rect.set(487.95f, 997.49f, 568.78f, 997.49f);
            Path path876578Path = CacheForCanvas1.path876578Path;
            path876578Path.reset();
            path876578Path.moveTo(568.78f, 997.49f);
            path876578Path.lineTo(487.95f, 997.49f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876578Path, paint);
            canvas.restore();

            // path876-70-9-1
            RectF path8767091Rect = CacheForCanvas1.path8767091Rect;
            path8767091Rect.set(502.95f, 1023.47f, 583.78f, 1023.47f);
            Path path8767091Path = CacheForCanvas1.path8767091Path;
            path8767091Path.reset();
            path8767091Path.moveTo(583.78f, 1023.47f);
            path8767091Path.lineTo(502.95f, 1023.47f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767091Path, paint);
            canvas.restore();

            // path876-3-9-2-2
            RectF path8763922Rect = CacheForCanvas1.path8763922Rect;
            path8763922Rect.set(505.45f, 1027.8f, 586.28f, 1027.8f);
            Path path8763922Path = CacheForCanvas1.path8763922Path;
            path8763922Path.reset();
            path8763922Path.moveTo(586.28f, 1027.8f);
            path8763922Path.lineTo(505.45f, 1027.8f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763922Path, paint);
            canvas.restore();

            // path876-6-3-0-9
            RectF path8766309Rect = CacheForCanvas1.path8766309Rect;
            path8766309Rect.set(507.95f, 1032.13f, 588.78f, 1032.13f);
            Path path8766309Path = CacheForCanvas1.path8766309Path;
            path8766309Path.reset();
            path8766309Path.moveTo(588.78f, 1032.13f);
            path8766309Path.lineTo(507.95f, 1032.13f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766309Path, paint);
            canvas.restore();

            // path876-7-6-2-3
            RectF path8767623Rect = CacheForCanvas1.path8767623Rect;
            path8767623Rect.set(510.45f, 1036.46f, 591.28f, 1036.46f);
            Path path8767623Path = CacheForCanvas1.path8767623Path;
            path8767623Path.reset();
            path8767623Path.moveTo(591.28f, 1036.46f);
            path8767623Path.lineTo(510.45f, 1036.46f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767623Path, paint);
            canvas.restore();

            // path876-5-0-3-90
            RectF path87650390Rect = CacheForCanvas1.path87650390Rect;
            path87650390Rect.set(500.45f, 1019.14f, 581.28f, 1019.14f);
            Path path87650390Path = CacheForCanvas1.path87650390Path;
            path87650390Path.reset();
            path87650390Path.moveTo(581.28f, 1019.14f);
            path87650390Path.lineTo(500.45f, 1019.14f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87650390Path, paint);
            canvas.restore();

            // rect1446
            RectF rect1446Rect = CacheForCanvas1.rect1446Rect;
            rect1446Rect.set(390.27f, 798.3f, 473f, 841.6f);
            Path rect1446Path = CacheForCanvas1.rect1446Path;
            rect1446Path.reset();
            rect1446Path.moveTo(390.27f, 798.3f);
            rect1446Path.lineTo(448f, 798.3f);
            rect1446Path.lineTo(473f, 841.6f);
            rect1446Path.lineTo(415.27f, 841.6f);
            rect1446Path.lineTo(390.27f, 798.3f);
            rect1446Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor4);
            canvas.drawPath(rect1446Path, paint);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.07f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(rect1446Path, paint);
            canvas.restore();

            // path1459
            RectF path1459Rect = CacheForCanvas1.path1459Rect;
            path1459Rect.set(668.58f, 625.1f, 1519.86f, 1014.81f);
            Path path1459Path = CacheForCanvas1.path1459Path;
            path1459Path.reset();
            path1459Path.moveTo(668.58f, 720.36f);
            path1459Path.lineTo(841.79f, 720.36f);
            path1459Path.lineTo(786.79f, 625.1f);
            path1459Path.lineTo(1294.86f, 625.1f);
            path1459Path.lineTo(1314.86f, 659.74f);
            path1459Path.lineTo(852.98f, 659.74f);
            path1459Path.lineTo(1037.98f, 980.17f);
            path1459Path.lineTo(1499.86f, 980.17f);
            path1459Path.lineTo(1519.86f, 1014.81f);
            path1459Path.lineTo(1011.79f, 1014.81f);
            path1459Path.lineTo(956.79f, 919.55f);
            path1459Path.lineTo(783.58f, 919.55f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path1459Path, paint);
            canvas.restore();

            // path1461
            RectF path1461Rect = CacheForCanvas1.path1461Rect;
            path1461Rect.set(688.58f, 755f, 936.79f, 884.91f);
            Path path1461Path = CacheForCanvas1.path1461Path;
            path1461Path.reset();
            path1461Path.moveTo(763.58f, 884.91f);
            path1461Path.lineTo(936.79f, 884.91f);
            path1461Path.lineTo(861.79f, 755f);
            path1461Path.lineTo(688.58f, 755f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path1461Path, paint);
            canvas.restore();

            // path1463
            RectF path1463Rect = CacheForCanvas1.path1463Rect;
            path1463Rect.set(1314.86f, 659.74f, 1499.86f, 980.17f);
            Path path1463Path = CacheForCanvas1.path1463Path;
            path1463Path.reset();
            path1463Path.moveTo(1314.86f, 659.74f);
            path1463Path.lineTo(1499.86f, 980.17f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path1463Path, paint);
            canvas.restore();

            // rect1519
            RectF rect1519Rect = CacheForCanvas1.rect1519Rect;
            rect1519Rect.set(852.98f, 659.74f, 1499.86f, 980.17f);
            Path rect1519Path = CacheForCanvas1.rect1519Path;
            rect1519Path.reset();
            rect1519Path.moveTo(852.98f, 659.74f);
            rect1519Path.lineTo(1314.86f, 659.74f);
            rect1519Path.lineTo(1499.86f, 980.17f);
            rect1519Path.lineTo(1037.98f, 980.17f);
            rect1519Path.lineTo(852.98f, 659.74f);
            rect1519Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor5);
            canvas.drawPath(rect1519Path, paint);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(4.03f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(rect1519Path, paint);
            canvas.restore();

            // path830-8
            RectF path8308Rect = CacheForCanvas1.path8308Rect;
            path8308Rect.set(216.94f, 638.09f, 251.94f, 698.71f);
            Path path8308Path = CacheForCanvas1.path8308Path;
            path8308Path.reset();
            path8308Path.moveTo(251.94f, 698.71f);
            path8308Path.lineTo(216.94f, 638.09f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8308Path, paint);
            canvas.restore();

            // path876-8
            RectF path8768Rect = CacheForCanvas1.path8768Rect;
            path8768Rect.set(244.44f, 685.72f, 302.17f, 685.72f);
            Path path8768Path = CacheForCanvas1.path8768Path;
            path8768Path.reset();
            path8768Path.moveTo(302.17f, 685.72f);
            path8768Path.lineTo(244.44f, 685.72f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8768Path, paint);
            canvas.restore();

            // path876-3-5
            RectF path87635Rect = CacheForCanvas1.path87635Rect;
            path87635Rect.set(241.94f, 681.39f, 299.67f, 681.39f);
            Path path87635Path = CacheForCanvas1.path87635Path;
            path87635Path.reset();
            path87635Path.moveTo(299.67f, 681.39f);
            path87635Path.lineTo(241.94f, 681.39f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87635Path, paint);
            canvas.restore();

            // path876-6-0
            RectF path87660Rect = CacheForCanvas1.path87660Rect;
            path87660Rect.set(239.44f, 677.06f, 297.17f, 677.06f);
            Path path87660Path = CacheForCanvas1.path87660Path;
            path87660Path.reset();
            path87660Path.moveTo(297.17f, 677.06f);
            path87660Path.lineTo(239.44f, 677.06f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87660Path, paint);
            canvas.restore();

            // path876-7-96
            RectF path876796Rect = CacheForCanvas1.path876796Rect;
            path876796Rect.set(236.94f, 672.73f, 294.67f, 672.73f);
            Path path876796Path = CacheForCanvas1.path876796Path;
            path876796Path.reset();
            path876796Path.moveTo(294.67f, 672.73f);
            path876796Path.lineTo(236.94f, 672.73f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876796Path, paint);
            canvas.restore();

            // path876-5-3
            RectF path87653Rect = CacheForCanvas1.path87653Rect;
            path87653Rect.set(246.94f, 690.05f, 304.67f, 690.05f);
            Path path87653Path = CacheForCanvas1.path87653Path;
            path87653Path.reset();
            path87653Path.moveTo(304.67f, 690.05f);
            path87653Path.lineTo(246.94f, 690.05f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87653Path, paint);
            canvas.restore();

            // path876-70-8
            RectF path876708Rect = CacheForCanvas1.path876708Rect;
            path876708Rect.set(231.94f, 664.07f, 289.67f, 664.07f);
            Path path876708Path = CacheForCanvas1.path876708Path;
            path876708Path.reset();
            path876708Path.moveTo(289.67f, 664.07f);
            path876708Path.lineTo(231.94f, 664.07f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876708Path, paint);
            canvas.restore();

            // path876-3-9-5
            RectF path876395Rect = CacheForCanvas1.path876395Rect;
            path876395Rect.set(229.44f, 659.74f, 287.17f, 659.74f);
            Path path876395Path = CacheForCanvas1.path876395Path;
            path876395Path.reset();
            path876395Path.moveTo(287.17f, 659.74f);
            path876395Path.lineTo(229.44f, 659.74f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876395Path, paint);
            canvas.restore();

            // path876-6-3-6
            RectF path876636Rect = CacheForCanvas1.path876636Rect;
            path876636Rect.set(226.94f, 655.41f, 284.67f, 655.41f);
            Path path876636Path = CacheForCanvas1.path876636Path;
            path876636Path.reset();
            path876636Path.moveTo(284.67f, 655.41f);
            path876636Path.lineTo(226.94f, 655.41f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876636Path, paint);
            canvas.restore();

            // path876-7-6-1
            RectF path876761Rect = CacheForCanvas1.path876761Rect;
            path876761Rect.set(224.44f, 651.08f, 282.17f, 651.08f);
            Path path876761Path = CacheForCanvas1.path876761Path;
            path876761Path.reset();
            path876761Path.moveTo(282.17f, 651.08f);
            path876761Path.lineTo(224.44f, 651.08f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876761Path, paint);
            canvas.restore();

            // path876-5-0-1
            RectF path876501Rect = CacheForCanvas1.path876501Rect;
            path876501Rect.set(234.44f, 668.4f, 292.17f, 668.4f);
            Path path876501Path = CacheForCanvas1.path876501Path;
            path876501Path.reset();
            path876501Path.moveTo(292.17f, 668.4f);
            path876501Path.lineTo(234.44f, 668.4f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876501Path, paint);
            canvas.restore();

            // path876-62-5
            RectF path876625Rect = CacheForCanvas1.path876625Rect;
            path876625Rect.set(219.44f, 642.42f, 277.17f, 642.42f);
            Path path876625Path = CacheForCanvas1.path876625Path;
            path876625Path.reset();
            path876625Path.moveTo(277.17f, 642.42f);
            path876625Path.lineTo(219.44f, 642.42f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876625Path, paint);
            canvas.restore();

            // path876-3-6-9
            RectF path876369Rect = CacheForCanvas1.path876369Rect;
            path876369Rect.set(216.94f, 638.09f, 274.67f, 638.09f);
            Path path876369Path = CacheForCanvas1.path876369Path;
            path876369Path.reset();
            path876369Path.moveTo(274.67f, 638.09f);
            path876369Path.lineTo(216.94f, 638.09f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876369Path, paint);
            canvas.restore();

            // path876-6-1-8
            RectF path876618Rect = CacheForCanvas1.path876618Rect;
            path876618Rect.set(214.44f, 633.76f, 272.17f, 633.76f);
            Path path876618Path = CacheForCanvas1.path876618Path;
            path876618Path.reset();
            path876618Path.moveTo(272.17f, 633.76f);
            path876618Path.lineTo(214.44f, 633.76f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876618Path, paint);
            canvas.restore();

            // path876-7-8-48
            RectF path8767848Rect = CacheForCanvas1.path8767848Rect;
            path8767848Rect.set(211.94f, 629.43f, 269.67f, 629.43f);
            Path path8767848Path = CacheForCanvas1.path8767848Path;
            path8767848Path.reset();
            path8767848Path.moveTo(269.67f, 629.43f);
            path8767848Path.lineTo(211.94f, 629.43f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767848Path, paint);
            canvas.restore();

            // path876-5-7-1
            RectF path876571Rect = CacheForCanvas1.path876571Rect;
            path876571Rect.set(221.94f, 646.75f, 279.67f, 646.75f);
            Path path876571Path = CacheForCanvas1.path876571Path;
            path876571Path.reset();
            path876571Path.moveTo(279.67f, 646.75f);
            path876571Path.lineTo(221.94f, 646.75f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876571Path, paint);
            canvas.restore();

            // path876-70-9-0
            RectF path8767090Rect = CacheForCanvas1.path8767090Rect;
            path8767090Rect.set(206.94f, 620.77f, 264.67f, 620.77f);
            Path path8767090Path = CacheForCanvas1.path8767090Path;
            path8767090Path.reset();
            path8767090Path.moveTo(264.67f, 620.77f);
            path8767090Path.lineTo(206.94f, 620.77f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767090Path, paint);
            canvas.restore();

            // path876-3-9-2-3
            RectF path8763923Rect = CacheForCanvas1.path8763923Rect;
            path8763923Rect.set(204.44f, 616.44f, 262.17f, 616.44f);
            Path path8763923Path = CacheForCanvas1.path8763923Path;
            path8763923Path.reset();
            path8763923Path.moveTo(262.17f, 616.44f);
            path8763923Path.lineTo(204.44f, 616.44f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763923Path, paint);
            canvas.restore();

            // path876-6-3-0-0
            RectF path8766300Rect = CacheForCanvas1.path8766300Rect;
            path8766300Rect.set(201.94f, 612.11f, 259.67f, 612.11f);
            Path path8766300Path = CacheForCanvas1.path8766300Path;
            path8766300Path.reset();
            path8766300Path.moveTo(259.67f, 612.11f);
            path8766300Path.lineTo(201.94f, 612.11f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766300Path, paint);
            canvas.restore();

            // path876-7-6-2-44
            RectF path87676244Rect = CacheForCanvas1.path87676244Rect;
            path87676244Rect.set(199.44f, 607.78f, 257.17f, 607.78f);
            Path path87676244Path = CacheForCanvas1.path87676244Path;
            path87676244Path.reset();
            path87676244Path.moveTo(257.17f, 607.78f);
            path87676244Path.lineTo(199.44f, 607.78f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87676244Path, paint);
            canvas.restore();

            // path876-5-0-3-4
            RectF path8765034Rect = CacheForCanvas1.path8765034Rect;
            path8765034Rect.set(209.44f, 625.1f, 267.17f, 625.1f);
            Path path8765034Path = CacheForCanvas1.path8765034Path;
            path8765034Path.reset();
            path8765034Path.moveTo(267.17f, 625.1f);
            path8765034Path.lineTo(209.44f, 625.1f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765034Path, paint);
            canvas.restore();

            // rect1196-4
            RectF rect11964Rect = CacheForCanvas1.rect11964Rect;
            rect11964Rect.set(257.17f, 607.78f, 310.45f, 690.05f);
            Path rect11964Path = CacheForCanvas1.rect11964Path;
            rect11964Path.reset();
            rect11964Path.moveTo(304.67f, 690.05f);
            rect11964Path.lineTo(310.45f, 690.05f);
            rect11964Path.lineTo(262.95f, 607.78f);
            rect11964Path.lineTo(257.17f, 607.78f);
            rect11964Path.lineTo(304.67f, 690.05f);
            rect11964Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor4);
            canvas.drawPath(rect11964Path, paint);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.07f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(rect11964Path, paint);
            canvas.restore();

            // path876-30-7
            RectF path876307Rect = CacheForCanvas1.path876307Rect;
            path876307Rect.set(394.55f, 685.72f, 452.29f, 685.72f);
            Path path876307Path = CacheForCanvas1.path876307Path;
            path876307Path.reset();
            path876307Path.moveTo(394.55f, 685.72f);
            path876307Path.lineTo(452.29f, 685.72f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876307Path, paint);
            canvas.restore();

            // path876-3-7-6
            RectF path876376Rect = CacheForCanvas1.path876376Rect;
            path876376Rect.set(392.05f, 681.39f, 449.79f, 681.39f);
            Path path876376Path = CacheForCanvas1.path876376Path;
            path876376Path.reset();
            path876376Path.moveTo(392.05f, 681.39f);
            path876376Path.lineTo(449.79f, 681.39f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876376Path, paint);
            canvas.restore();

            // path876-6-8-3
            RectF path876683Rect = CacheForCanvas1.path876683Rect;
            path876683Rect.set(389.55f, 677.06f, 447.29f, 677.06f);
            Path path876683Path = CacheForCanvas1.path876683Path;
            path876683Path.reset();
            path876683Path.moveTo(389.55f, 677.06f);
            path876683Path.lineTo(447.29f, 677.06f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876683Path, paint);
            canvas.restore();

            // path876-7-68-1
            RectF path8767681Rect = CacheForCanvas1.path8767681Rect;
            path8767681Rect.set(387.05f, 672.73f, 444.79f, 672.73f);
            Path path8767681Path = CacheForCanvas1.path8767681Path;
            path8767681Path.reset();
            path8767681Path.moveTo(387.05f, 672.73f);
            path8767681Path.lineTo(444.79f, 672.73f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767681Path, paint);
            canvas.restore();

            // path876-5-8-7
            RectF path876587Rect = CacheForCanvas1.path876587Rect;
            path876587Rect.set(397.05f, 690.05f, 454.79f, 690.05f);
            Path path876587Path = CacheForCanvas1.path876587Path;
            path876587Path.reset();
            path876587Path.moveTo(397.05f, 690.05f);
            path876587Path.lineTo(454.79f, 690.05f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876587Path, paint);
            canvas.restore();

            // path876-70-4-5
            RectF path8767045Rect = CacheForCanvas1.path8767045Rect;
            path8767045Rect.set(382.05f, 664.07f, 439.79f, 664.07f);
            Path path8767045Path = CacheForCanvas1.path8767045Path;
            path8767045Path.reset();
            path8767045Path.moveTo(382.05f, 664.07f);
            path8767045Path.lineTo(439.79f, 664.07f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767045Path, paint);
            canvas.restore();

            // path876-3-9-3-9
            RectF path8763939Rect = CacheForCanvas1.path8763939Rect;
            path8763939Rect.set(379.55f, 659.74f, 437.29f, 659.74f);
            Path path8763939Path = CacheForCanvas1.path8763939Path;
            path8763939Path.reset();
            path8763939Path.moveTo(379.55f, 659.74f);
            path8763939Path.lineTo(437.29f, 659.74f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763939Path, paint);
            canvas.restore();

            // path876-6-3-1-6
            RectF path8766316Rect = CacheForCanvas1.path8766316Rect;
            path8766316Rect.set(377.05f, 655.41f, 434.79f, 655.41f);
            Path path8766316Path = CacheForCanvas1.path8766316Path;
            path8766316Path.reset();
            path8766316Path.moveTo(377.05f, 655.41f);
            path8766316Path.lineTo(434.79f, 655.41f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766316Path, paint);
            canvas.restore();

            // path876-7-6-4-2
            RectF path8767642Rect = CacheForCanvas1.path8767642Rect;
            path8767642Rect.set(374.55f, 651.08f, 432.29f, 651.08f);
            Path path8767642Path = CacheForCanvas1.path8767642Path;
            path8767642Path.reset();
            path8767642Path.moveTo(374.55f, 651.08f);
            path8767642Path.lineTo(432.29f, 651.08f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767642Path, paint);
            canvas.restore();

            // path876-5-0-9-1
            RectF path8765091Rect = CacheForCanvas1.path8765091Rect;
            path8765091Rect.set(384.55f, 668.4f, 442.29f, 668.4f);
            Path path8765091Path = CacheForCanvas1.path8765091Path;
            path8765091Path.reset();
            path8765091Path.moveTo(384.55f, 668.4f);
            path8765091Path.lineTo(442.29f, 668.4f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765091Path, paint);
            canvas.restore();

            // path876-62-2-7
            RectF path8766227Rect = CacheForCanvas1.path8766227Rect;
            path8766227Rect.set(369.55f, 642.42f, 427.29f, 642.42f);
            Path path8766227Path = CacheForCanvas1.path8766227Path;
            path8766227Path.reset();
            path8766227Path.moveTo(369.55f, 642.42f);
            path8766227Path.lineTo(427.29f, 642.42f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766227Path, paint);
            canvas.restore();

            // path876-3-6-0-8
            RectF path8763608Rect = CacheForCanvas1.path8763608Rect;
            path8763608Rect.set(367.05f, 638.09f, 424.79f, 638.09f);
            Path path8763608Path = CacheForCanvas1.path8763608Path;
            path8763608Path.reset();
            path8763608Path.moveTo(367.05f, 638.09f);
            path8763608Path.lineTo(424.79f, 638.09f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763608Path, paint);
            canvas.restore();

            // path876-6-1-6-5
            RectF path8766165Rect = CacheForCanvas1.path8766165Rect;
            path8766165Rect.set(364.55f, 633.76f, 422.29f, 633.76f);
            Path path8766165Path = CacheForCanvas1.path8766165Path;
            path8766165Path.reset();
            path8766165Path.moveTo(364.55f, 633.76f);
            path8766165Path.lineTo(422.29f, 633.76f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766165Path, paint);
            canvas.restore();

            // path876-7-8-8-7
            RectF path8767887Rect = CacheForCanvas1.path8767887Rect;
            path8767887Rect.set(362.05f, 629.43f, 419.79f, 629.43f);
            Path path8767887Path = CacheForCanvas1.path8767887Path;
            path8767887Path.reset();
            path8767887Path.moveTo(362.05f, 629.43f);
            path8767887Path.lineTo(419.79f, 629.43f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767887Path, paint);
            canvas.restore();

            // path876-5-7-9-4
            RectF path8765794Rect = CacheForCanvas1.path8765794Rect;
            path8765794Rect.set(372.05f, 646.75f, 429.79f, 646.75f);
            Path path8765794Path = CacheForCanvas1.path8765794Path;
            path8765794Path.reset();
            path8765794Path.moveTo(372.05f, 646.75f);
            path8765794Path.lineTo(429.79f, 646.75f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765794Path, paint);
            canvas.restore();

            // path876-70-9-2-1
            RectF path87670921Rect = CacheForCanvas1.path87670921Rect;
            path87670921Rect.set(357.05f, 620.77f, 414.79f, 620.77f);
            Path path87670921Path = CacheForCanvas1.path87670921Path;
            path87670921Path.reset();
            path87670921Path.moveTo(357.05f, 620.77f);
            path87670921Path.lineTo(414.79f, 620.77f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87670921Path, paint);
            canvas.restore();

            // path876-3-9-2-6-8
            RectF path87639268Rect = CacheForCanvas1.path87639268Rect;
            path87639268Rect.set(354.55f, 616.44f, 412.29f, 616.44f);
            Path path87639268Path = CacheForCanvas1.path87639268Path;
            path87639268Path.reset();
            path87639268Path.moveTo(354.55f, 616.44f);
            path87639268Path.lineTo(412.29f, 616.44f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87639268Path, paint);
            canvas.restore();

            // path876-6-3-0-6-5
            RectF path87663065Rect = CacheForCanvas1.path87663065Rect;
            path87663065Rect.set(352.05f, 612.11f, 409.79f, 612.11f);
            Path path87663065Path = CacheForCanvas1.path87663065Path;
            path87663065Path.reset();
            path87663065Path.moveTo(352.05f, 612.11f);
            path87663065Path.lineTo(409.79f, 612.11f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87663065Path, paint);
            canvas.restore();

            // path876-7-6-2-4-9
            RectF path87676249Rect = CacheForCanvas1.path87676249Rect;
            path87676249Rect.set(349.55f, 607.78f, 407.29f, 607.78f);
            Path path87676249Path = CacheForCanvas1.path87676249Path;
            path87676249Path.reset();
            path87676249Path.moveTo(349.55f, 607.78f);
            path87676249Path.lineTo(407.29f, 607.78f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87676249Path, paint);
            canvas.restore();

            // path876-5-0-3-9-7
            RectF path87650397Rect = CacheForCanvas1.path87650397Rect;
            path87650397Rect.set(359.55f, 625.1f, 417.29f, 625.1f);
            Path path87650397Path = CacheForCanvas1.path87650397Path;
            path87650397Path.reset();
            path87650397Path.moveTo(359.55f, 625.1f);
            path87650397Path.lineTo(417.29f, 625.1f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87650397Path, paint);
            canvas.restore();

            // rect1196-5-5
            RectF rect119655Rect = CacheForCanvas1.rect119655Rect;
            rect119655Rect.set(343.78f, 607.78f, 397.05f, 690.05f);
            Path rect119655Path = CacheForCanvas1.rect119655Path;
            rect119655Path.reset();
            rect119655Path.moveTo(397.05f, 690.05f);
            rect119655Path.lineTo(391.28f, 690.05f);
            rect119655Path.lineTo(343.78f, 607.78f);
            rect119655Path.lineTo(349.55f, 607.78f);
            rect119655Path.lineTo(397.05f, 690.05f);
            rect119655Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor4);
            canvas.drawPath(rect119655Path, paint);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.07f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(rect119655Path, paint);
            canvas.restore();

            // path876-1-3
            RectF path87613Rect = CacheForCanvas1.path87613Rect;
            path87613Rect.set(307.95f, 685.72f, 388.78f, 685.72f);
            Path path87613Path = CacheForCanvas1.path87613Path;
            path87613Path.reset();
            path87613Path.moveTo(388.78f, 685.72f);
            path87613Path.lineTo(307.95f, 685.72f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87613Path, paint);
            canvas.restore();

            // path876-3-1-8
            RectF path876318Rect = CacheForCanvas1.path876318Rect;
            path876318Rect.set(305.45f, 681.39f, 386.28f, 681.39f);
            Path path876318Path = CacheForCanvas1.path876318Path;
            path876318Path.reset();
            path876318Path.moveTo(386.28f, 681.39f);
            path876318Path.lineTo(305.45f, 681.39f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876318Path, paint);
            canvas.restore();

            // path876-6-5-8
            RectF path876658Rect = CacheForCanvas1.path876658Rect;
            path876658Rect.set(302.95f, 677.06f, 383.78f, 677.06f);
            Path path876658Path = CacheForCanvas1.path876658Path;
            path876658Path.reset();
            path876658Path.moveTo(383.78f, 677.06f);
            path876658Path.lineTo(302.95f, 677.06f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876658Path, paint);
            canvas.restore();

            // path876-7-9-3
            RectF path876793Rect = CacheForCanvas1.path876793Rect;
            path876793Rect.set(300.45f, 672.73f, 381.28f, 672.73f);
            Path path876793Path = CacheForCanvas1.path876793Path;
            path876793Path.reset();
            path876793Path.moveTo(381.28f, 672.73f);
            path876793Path.lineTo(300.45f, 672.73f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876793Path, paint);
            canvas.restore();

            // path876-5-77-1
            RectF path8765771Rect = CacheForCanvas1.path8765771Rect;
            path8765771Rect.set(310.45f, 690.05f, 391.28f, 690.05f);
            Path path8765771Path = CacheForCanvas1.path8765771Path;
            path8765771Path.reset();
            path8765771Path.moveTo(391.28f, 690.05f);
            path8765771Path.lineTo(310.45f, 690.05f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765771Path, paint);
            canvas.restore();

            // path876-70-6-8
            RectF path8767068Rect = CacheForCanvas1.path8767068Rect;
            path8767068Rect.set(295.45f, 664.07f, 376.28f, 664.07f);
            Path path8767068Path = CacheForCanvas1.path8767068Path;
            path8767068Path.reset();
            path8767068Path.moveTo(376.28f, 664.07f);
            path8767068Path.lineTo(295.45f, 664.07f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767068Path, paint);
            canvas.restore();

            // path876-3-9-7-9
            RectF path8763979Rect = CacheForCanvas1.path8763979Rect;
            path8763979Rect.set(292.95f, 659.74f, 373.78f, 659.74f);
            Path path8763979Path = CacheForCanvas1.path8763979Path;
            path8763979Path.reset();
            path8763979Path.moveTo(373.78f, 659.74f);
            path8763979Path.lineTo(292.95f, 659.74f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763979Path, paint);
            canvas.restore();

            // path876-6-3-3-6
            RectF path8766336Rect = CacheForCanvas1.path8766336Rect;
            path8766336Rect.set(290.45f, 655.41f, 371.28f, 655.41f);
            Path path8766336Path = CacheForCanvas1.path8766336Path;
            path8766336Path.reset();
            path8766336Path.moveTo(371.28f, 655.41f);
            path8766336Path.lineTo(290.45f, 655.41f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766336Path, paint);
            canvas.restore();

            // path876-7-6-6-4
            RectF path8767664Rect = CacheForCanvas1.path8767664Rect;
            path8767664Rect.set(287.95f, 651.08f, 368.78f, 651.08f);
            Path path8767664Path = CacheForCanvas1.path8767664Path;
            path8767664Path.reset();
            path8767664Path.moveTo(368.78f, 651.08f);
            path8767664Path.lineTo(287.95f, 651.08f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767664Path, paint);
            canvas.restore();

            // path876-5-0-5-3
            RectF path8765053Rect = CacheForCanvas1.path8765053Rect;
            path8765053Rect.set(297.95f, 668.4f, 378.78f, 668.4f);
            Path path8765053Path = CacheForCanvas1.path8765053Path;
            path8765053Path.reset();
            path8765053Path.moveTo(378.78f, 668.4f);
            path8765053Path.lineTo(297.95f, 668.4f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765053Path, paint);
            canvas.restore();

            // path876-62-6-3
            RectF path8766263Rect = CacheForCanvas1.path8766263Rect;
            path8766263Rect.set(282.95f, 642.42f, 363.78f, 642.42f);
            Path path8766263Path = CacheForCanvas1.path8766263Path;
            path8766263Path.reset();
            path8766263Path.moveTo(363.78f, 642.42f);
            path8766263Path.lineTo(282.95f, 642.42f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766263Path, paint);
            canvas.restore();

            // path876-3-6-3-3
            RectF path8763633Rect = CacheForCanvas1.path8763633Rect;
            path8763633Rect.set(280.45f, 638.09f, 361.28f, 638.09f);
            Path path8763633Path = CacheForCanvas1.path8763633Path;
            path8763633Path.reset();
            path8763633Path.moveTo(361.28f, 638.09f);
            path8763633Path.lineTo(280.45f, 638.09f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763633Path, paint);
            canvas.restore();

            // path876-6-1-9-8
            RectF path8766198Rect = CacheForCanvas1.path8766198Rect;
            path8766198Rect.set(277.95f, 633.76f, 358.78f, 633.76f);
            Path path8766198Path = CacheForCanvas1.path8766198Path;
            path8766198Path.reset();
            path8766198Path.moveTo(358.78f, 633.76f);
            path8766198Path.lineTo(277.95f, 633.76f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766198Path, paint);
            canvas.restore();

            // path876-7-8-4-6
            RectF path8767846Rect = CacheForCanvas1.path8767846Rect;
            path8767846Rect.set(275.45f, 629.43f, 356.28f, 629.43f);
            Path path8767846Path = CacheForCanvas1.path8767846Path;
            path8767846Path.reset();
            path8767846Path.moveTo(356.28f, 629.43f);
            path8767846Path.lineTo(275.45f, 629.43f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767846Path, paint);
            canvas.restore();

            // path876-5-7-8-0
            RectF path8765780Rect = CacheForCanvas1.path8765780Rect;
            path8765780Rect.set(285.45f, 646.75f, 366.28f, 646.75f);
            Path path8765780Path = CacheForCanvas1.path8765780Path;
            path8765780Path.reset();
            path8765780Path.moveTo(366.28f, 646.75f);
            path8765780Path.lineTo(285.45f, 646.75f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765780Path, paint);
            canvas.restore();

            // path876-70-9-1-4
            RectF path87670914Rect = CacheForCanvas1.path87670914Rect;
            path87670914Rect.set(270.45f, 620.77f, 351.28f, 620.77f);
            Path path87670914Path = CacheForCanvas1.path87670914Path;
            path87670914Path.reset();
            path87670914Path.moveTo(351.28f, 620.77f);
            path87670914Path.lineTo(270.45f, 620.77f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87670914Path, paint);
            canvas.restore();

            // path876-3-9-2-2-8
            RectF path87639228Rect = CacheForCanvas1.path87639228Rect;
            path87639228Rect.set(267.95f, 616.44f, 348.78f, 616.44f);
            Path path87639228Path = CacheForCanvas1.path87639228Path;
            path87639228Path.reset();
            path87639228Path.moveTo(348.78f, 616.44f);
            path87639228Path.lineTo(267.95f, 616.44f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87639228Path, paint);
            canvas.restore();

            // path876-6-3-0-9-8
            RectF path87663098Rect = CacheForCanvas1.path87663098Rect;
            path87663098Rect.set(265.45f, 612.11f, 346.28f, 612.11f);
            Path path87663098Path = CacheForCanvas1.path87663098Path;
            path87663098Path.reset();
            path87663098Path.moveTo(346.28f, 612.11f);
            path87663098Path.lineTo(265.45f, 612.11f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87663098Path, paint);
            canvas.restore();

            // path876-7-6-2-3-8
            RectF path87676238Rect = CacheForCanvas1.path87676238Rect;
            path87676238Rect.set(262.95f, 607.78f, 343.78f, 607.78f);
            Path path87676238Path = CacheForCanvas1.path87676238Path;
            path87676238Path.reset();
            path87676238Path.moveTo(343.78f, 607.78f);
            path87676238Path.lineTo(262.95f, 607.78f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87676238Path, paint);
            canvas.restore();

            // path876-5-0-3-90-9
            RectF path876503909Rect = CacheForCanvas1.path876503909Rect;
            path876503909Rect.set(272.95f, 625.1f, 353.78f, 625.1f);
            Path path876503909Path = CacheForCanvas1.path876503909Path;
            path876503909Path.reset();
            path876503909Path.moveTo(353.78f, 625.1f);
            path876503909Path.lineTo(272.95f, 625.1f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876503909Path, paint);
            canvas.restore();

            // path828-7
            RectF path8287Rect = CacheForCanvas1.path8287Rect;
            path8287Rect.set(173.93f, 0.26f, 676.78f, 511.22f);
            Path path8287Path = CacheForCanvas1.path8287Path;
            path8287Path.reset();
            path8287Path.moveTo(676.78f, 511.22f);
            path8287Path.lineTo(468.93f, 511.22f);
            path8287Path.lineTo(428.93f, 441.94f);
            path8287Path.moveTo(213.93f, 69.54f);
            path8287Path.lineTo(173.93f, 0.26f);
            path8287Path.lineTo(381.78f, 0.26f);
            path8287Path.lineTo(676.78f, 511.22f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8287Path, paint);
            canvas.restore();

            // path830-7
            RectF path8307Rect = CacheForCanvas1.path8307Rect;
            path8307Rect.set(393.93f, 381.31f, 428.93f, 441.94f);
            Path path8307Path = CacheForCanvas1.path8307Path;
            path8307Path.reset();
            path8307Path.moveTo(393.93f, 381.31f);
            path8307Path.lineTo(428.93f, 441.94f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8307Path, paint);
            canvas.restore();

            // path844-9
            RectF path8449Rect = CacheForCanvas1.path8449Rect;
            path8449Rect.set(381.78f, 0.26f, 491.78f, 190.79f);
            Path path8449Path = CacheForCanvas1.path8449Path;
            path8449Path.reset();
            path8449Path.moveTo(491.78f, 190.79f);
            path8449Path.lineTo(381.78f, 0.26f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8449Path, paint);
            canvas.restore();

            // path846-2
            RectF path8462Rect = CacheForCanvas1.path8462Rect;
            path8462Rect.set(566.78f, 320.69f, 676.78f, 511.22f);
            Path path8462Path = CacheForCanvas1.path8462Path;
            path8462Path.reset();
            path8462Path.moveTo(566.78f, 320.69f);
            path8462Path.lineTo(676.78f, 511.22f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8462Path, paint);
            canvas.restore();

            // path876-54
            RectF path87654Rect = CacheForCanvas1.path87654Rect;
            path87654Rect.set(401.43f, 394.3f, 459.17f, 394.31f);
            Path path87654Path = CacheForCanvas1.path87654Path;
            path87654Path.reset();
            path87654Path.moveTo(459.17f, 394.31f);
            path87654Path.lineTo(401.43f, 394.31f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87654Path, paint);
            canvas.restore();

            // path876-3- 2
            RectF path87632Rect = CacheForCanvas1.path87632Rect;
            path87632Rect.set(403.93f, 398.63f, 461.67f, 398.64f);
            Path path87632Path = CacheForCanvas1.path87632Path;
            path87632Path.reset();
            path87632Path.moveTo(461.67f, 398.64f);
            path87632Path.lineTo(403.93f, 398.64f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87632Path, paint);
            canvas.restore();

            // path876-6-59
            RectF path876659Rect = CacheForCanvas1.path876659Rect;
            path876659Rect.set(406.43f, 402.96f, 464.17f, 402.97f);
            Path path876659Path = CacheForCanvas1.path876659Path;
            path876659Path.reset();
            path876659Path.moveTo(464.17f, 402.97f);
            path876659Path.lineTo(406.43f, 402.97f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876659Path, paint);
            canvas.restore();

            // path876-7-4
            RectF path87674Rect = CacheForCanvas1.path87674Rect;
            path87674Rect.set(408.93f, 407.29f, 466.67f, 407.3f);
            Path path87674Path = CacheForCanvas1.path87674Path;
            path87674Path.reset();
            path87674Path.moveTo(466.67f, 407.3f);
            path87674Path.lineTo(408.93f, 407.3f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87674Path, paint);
            canvas.restore();

            // path876-5-6
            RectF path87656Rect = CacheForCanvas1.path87656Rect;
            path87656Rect.set(398.93f, 389.97f, 456.67f, 389.98f);
            Path path87656Path = CacheForCanvas1.path87656Path;
            path87656Path.reset();
            path87656Path.moveTo(456.67f, 389.98f);
            path87656Path.lineTo(398.93f, 389.98f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87656Path, paint);
            canvas.restore();

            // path876-70- 2
            RectF path876702Rect = CacheForCanvas1.path876702Rect;
            path876702Rect.set(413.93f, 415.96f, 471.67f, 415.96f);
            Path path876702Path = CacheForCanvas1.path876702Path;
            path876702Path.reset();
            path876702Path.moveTo(471.67f, 415.96f);
            path876702Path.lineTo(413.93f, 415.96f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876702Path, paint);
            canvas.restore();

            // path876-3-9-24
            RectF path8763924Rect = CacheForCanvas1.path8763924Rect;
            path8763924Rect.set(416.43f, 420.29f, 474.17f, 420.29f);
            Path path8763924Path = CacheForCanvas1.path8763924Path;
            path8763924Path.reset();
            path8763924Path.moveTo(474.17f, 420.29f);
            path8763924Path.lineTo(416.43f, 420.29f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763924Path, paint);
            canvas.restore();

            // path876-6-3-7
            RectF path876637Rect = CacheForCanvas1.path876637Rect;
            path876637Rect.set(418.93f, 424.62f, 476.67f, 424.62f);
            Path path876637Path = CacheForCanvas1.path876637Path;
            path876637Path.reset();
            path876637Path.moveTo(476.67f, 424.62f);
            path876637Path.lineTo(418.93f, 424.62f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876637Path, paint);
            canvas.restore();

            // path876-7-6-7
            RectF path876767Rect = CacheForCanvas1.path876767Rect;
            path876767Rect.set(421.43f, 428.95f, 479.17f, 428.95f);
            Path path876767Path = CacheForCanvas1.path876767Path;
            path876767Path.reset();
            path876767Path.moveTo(479.17f, 428.95f);
            path876767Path.lineTo(421.43f, 428.95f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876767Path, paint);
            canvas.restore();

            // path876-5-0-54
            RectF path8765054Rect = CacheForCanvas1.path8765054Rect;
            path8765054Rect.set(411.43f, 411.62f, 469.17f, 411.63f);
            Path path8765054Path = CacheForCanvas1.path8765054Path;
            path8765054Path.reset();
            path8765054Path.moveTo(469.17f, 411.63f);
            path8765054Path.lineTo(411.43f, 411.63f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765054Path, paint);
            canvas.restore();

            // path876-62-8
            RectF path876628Rect = CacheForCanvas1.path876628Rect;
            path876628Rect.set(426.43f, 437.61f, 484.17f, 437.61f);
            Path path876628Path = CacheForCanvas1.path876628Path;
            path876628Path.reset();
            path876628Path.moveTo(484.17f, 437.61f);
            path876628Path.lineTo(426.43f, 437.61f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876628Path, paint);
            canvas.restore();

            // path876-3-6-1
            RectF path876361Rect = CacheForCanvas1.path876361Rect;
            path876361Rect.set(428.93f, 441.94f, 486.67f, 441.94f);
            Path path876361Path = CacheForCanvas1.path876361Path;
            path876361Path.reset();
            path876361Path.moveTo(486.67f, 441.94f);
            path876361Path.lineTo(428.93f, 441.94f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876361Path, paint);
            canvas.restore();

            // path876-6-1-2
            RectF path876612Rect = CacheForCanvas1.path876612Rect;
            path876612Rect.set(431.43f, 446.27f, 489.17f, 446.27f);
            Path path876612Path = CacheForCanvas1.path876612Path;
            path876612Path.reset();
            path876612Path.moveTo(489.17f, 446.27f);
            path876612Path.lineTo(431.43f, 446.27f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876612Path, paint);
            canvas.restore();

            // path876-7-8-89
            RectF path8767889Rect = CacheForCanvas1.path8767889Rect;
            path8767889Rect.set(433.93f, 450.6f, 491.67f, 450.6f);
            Path path8767889Path = CacheForCanvas1.path8767889Path;
            path8767889Path.reset();
            path8767889Path.moveTo(491.67f, 450.6f);
            path8767889Path.lineTo(433.93f, 450.6f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767889Path, paint);
            canvas.restore();

            // path876-5-7-3
            RectF path876573Rect = CacheForCanvas1.path876573Rect;
            path876573Rect.set(423.93f, 433.28f, 481.67f, 433.28f);
            Path path876573Path = CacheForCanvas1.path876573Path;
            path876573Path.reset();
            path876573Path.moveTo(481.67f, 433.28f);
            path876573Path.lineTo(423.93f, 433.28f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876573Path, paint);
            canvas.restore();

            // path876-70-9-6
            RectF path8767096Rect = CacheForCanvas1.path8767096Rect;
            path8767096Rect.set(438.93f, 459.26f, 496.67f, 459.26f);
            Path path8767096Path = CacheForCanvas1.path8767096Path;
            path8767096Path.reset();
            path8767096Path.moveTo(496.67f, 459.26f);
            path8767096Path.lineTo(438.93f, 459.26f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767096Path, paint);
            canvas.restore();

            // path876-3-9-2-8
            RectF path8763928Rect = CacheForCanvas1.path8763928Rect;
            path8763928Rect.set(441.43f, 463.59f, 499.17f, 463.59f);
            Path path8763928Path = CacheForCanvas1.path8763928Path;
            path8763928Path.reset();
            path8763928Path.moveTo(499.17f, 463.59f);
            path8763928Path.lineTo(441.43f, 463.59f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763928Path, paint);
            canvas.restore();

            // path876-6-3-0-02
            RectF path87663002Rect = CacheForCanvas1.path87663002Rect;
            path87663002Rect.set(443.93f, 467.92f, 501.67f, 467.92f);
            Path path87663002Path = CacheForCanvas1.path87663002Path;
            path87663002Path.reset();
            path87663002Path.moveTo(501.67f, 467.92f);
            path87663002Path.lineTo(443.93f, 467.92f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87663002Path, paint);
            canvas.restore();

            // path876-7-6-2-1
            RectF path8767621Rect = CacheForCanvas1.path8767621Rect;
            path8767621Rect.set(446.43f, 472.25f, 504.17f, 472.25f);
            Path path8767621Path = CacheForCanvas1.path8767621Path;
            path8767621Path.reset();
            path8767621Path.moveTo(504.17f, 472.25f);
            path8767621Path.lineTo(446.43f, 472.25f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767621Path, paint);
            canvas.restore();

            // path876-5-0-3-0
            RectF path8765030Rect = CacheForCanvas1.path8765030Rect;
            path8765030Rect.set(436.43f, 454.93f, 494.17f, 454.93f);
            Path path8765030Path = CacheForCanvas1.path8765030Path;
            path8765030Path.reset();
            path8765030Path.moveTo(494.17f, 454.93f);
            path8765030Path.lineTo(436.43f, 454.93f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765030Path, paint);
            canvas.restore();

            // rect1196-51
            RectF rect119651Rect = CacheForCanvas1.rect119651Rect;
            rect119651Rect.set(456.67f, 389.97f, 509.94f, 472.25f);
            Path rect119651Path = CacheForCanvas1.rect119651Path;
            rect119651Path.reset();
            rect119651Path.moveTo(456.67f, 389.97f);
            rect119651Path.lineTo(462.44f, 389.97f);
            rect119651Path.lineTo(509.94f, 472.25f);
            rect119651Path.lineTo(504.17f, 472.25f);
            rect119651Path.lineTo(456.67f, 389.97f);
            rect119651Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor4);
            canvas.drawPath(rect119651Path, paint);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.07f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(rect119651Path, paint);
            canvas.restore();

            // path876-30-1
            RectF path876301Rect = CacheForCanvas1.path876301Rect;
            path876301Rect.set(551.55f, 394.3f, 609.28f, 394.31f);
            Path path876301Path = CacheForCanvas1.path876301Path;
            path876301Path.reset();
            path876301Path.moveTo(551.55f, 394.31f);
            path876301Path.lineTo(609.28f, 394.31f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876301Path, paint);
            canvas.restore();

            // path876-3-7-0
            RectF path876370Rect = CacheForCanvas1.path876370Rect;
            path876370Rect.set(554.05f, 398.63f, 611.78f, 398.64f);
            Path path876370Path = CacheForCanvas1.path876370Path;
            path876370Path.reset();
            path876370Path.moveTo(554.05f, 398.64f);
            path876370Path.lineTo(611.78f, 398.64f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876370Path, paint);
            canvas.restore();

            // path876-6-8-8
            RectF path876688Rect = CacheForCanvas1.path876688Rect;
            path876688Rect.set(556.55f, 402.96f, 614.28f, 402.97f);
            Path path876688Path = CacheForCanvas1.path876688Path;
            path876688Path.reset();
            path876688Path.moveTo(556.55f, 402.97f);
            path876688Path.lineTo(614.28f, 402.97f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876688Path, paint);
            canvas.restore();

            // path876-7-68-5
            RectF path8767685Rect = CacheForCanvas1.path8767685Rect;
            path8767685Rect.set(559.05f, 407.29f, 616.78f, 407.3f);
            Path path8767685Path = CacheForCanvas1.path8767685Path;
            path8767685Path.reset();
            path8767685Path.moveTo(559.05f, 407.3f);
            path8767685Path.lineTo(616.78f, 407.3f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767685Path, paint);
            canvas.restore();

            // path876-5-8-0
            RectF path876580Rect = CacheForCanvas1.path876580Rect;
            path876580Rect.set(549.05f, 389.97f, 606.78f, 389.98f);
            Path path876580Path = CacheForCanvas1.path876580Path;
            path876580Path.reset();
            path876580Path.moveTo(549.05f, 389.98f);
            path876580Path.lineTo(606.78f, 389.98f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876580Path, paint);
            canvas.restore();

            // path876-70-4-6
            RectF path8767046Rect = CacheForCanvas1.path8767046Rect;
            path8767046Rect.set(564.05f, 415.96f, 621.78f, 415.96f);
            Path path8767046Path = CacheForCanvas1.path8767046Path;
            path8767046Path.reset();
            path8767046Path.moveTo(564.05f, 415.96f);
            path8767046Path.lineTo(621.78f, 415.96f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767046Path, paint);
            canvas.restore();

            // path876-3-9-3-4
            RectF path8763934Rect = CacheForCanvas1.path8763934Rect;
            path8763934Rect.set(566.55f, 420.29f, 624.28f, 420.29f);
            Path path8763934Path = CacheForCanvas1.path8763934Path;
            path8763934Path.reset();
            path8763934Path.moveTo(566.55f, 420.29f);
            path8763934Path.lineTo(624.28f, 420.29f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763934Path, paint);
            canvas.restore();

            // path876-6-3-1-62
            RectF path87663162Rect = CacheForCanvas1.path87663162Rect;
            path87663162Rect.set(569.05f, 424.62f, 626.78f, 424.62f);
            Path path87663162Path = CacheForCanvas1.path87663162Path;
            path87663162Path.reset();
            path87663162Path.moveTo(569.05f, 424.62f);
            path87663162Path.lineTo(626.78f, 424.62f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87663162Path, paint);
            canvas.restore();

            // path876-7-6-4-5
            RectF path8767645Rect = CacheForCanvas1.path8767645Rect;
            path8767645Rect.set(571.55f, 428.95f, 629.28f, 428.95f);
            Path path8767645Path = CacheForCanvas1.path8767645Path;
            path8767645Path.reset();
            path8767645Path.moveTo(571.55f, 428.95f);
            path8767645Path.lineTo(629.28f, 428.95f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767645Path, paint);
            canvas.restore();

            // path876-5-0-9-8
            RectF path8765098Rect = CacheForCanvas1.path8765098Rect;
            path8765098Rect.set(561.55f, 411.62f, 619.28f, 411.63f);
            Path path8765098Path = CacheForCanvas1.path8765098Path;
            path8765098Path.reset();
            path8765098Path.moveTo(561.55f, 411.63f);
            path8765098Path.lineTo(619.28f, 411.63f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765098Path, paint);
            canvas.restore();

            // path876-62-2-6
            RectF path8766226Rect = CacheForCanvas1.path8766226Rect;
            path8766226Rect.set(576.55f, 437.61f, 634.28f, 437.61f);
            Path path8766226Path = CacheForCanvas1.path8766226Path;
            path8766226Path.reset();
            path8766226Path.moveTo(576.55f, 437.61f);
            path8766226Path.lineTo(634.28f, 437.61f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766226Path, paint);
            canvas.restore();

            // path876-3-6-0-2
            RectF path8763602Rect = CacheForCanvas1.path8763602Rect;
            path8763602Rect.set(579.05f, 441.94f, 636.78f, 441.94f);
            Path path8763602Path = CacheForCanvas1.path8763602Path;
            path8763602Path.reset();
            path8763602Path.moveTo(579.05f, 441.94f);
            path8763602Path.lineTo(636.78f, 441.94f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763602Path, paint);
            canvas.restore();

            // path876-6-1-6-8
            RectF path8766168Rect = CacheForCanvas1.path8766168Rect;
            path8766168Rect.set(581.55f, 446.27f, 639.28f, 446.27f);
            Path path8766168Path = CacheForCanvas1.path8766168Path;
            path8766168Path.reset();
            path8766168Path.moveTo(581.55f, 446.27f);
            path8766168Path.lineTo(639.28f, 446.27f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766168Path, paint);
            canvas.restore();

            // path876-7-8-8-4
            RectF path8767884Rect = CacheForCanvas1.path8767884Rect;
            path8767884Rect.set(584.05f, 450.6f, 641.78f, 450.6f);
            Path path8767884Path = CacheForCanvas1.path8767884Path;
            path8767884Path.reset();
            path8767884Path.moveTo(584.05f, 450.6f);
            path8767884Path.lineTo(641.78f, 450.6f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767884Path, paint);
            canvas.restore();

            // path876-5-7-9-7
            RectF path8765797Rect = CacheForCanvas1.path8765797Rect;
            path8765797Rect.set(574.05f, 433.28f, 631.78f, 433.28f);
            Path path8765797Path = CacheForCanvas1.path8765797Path;
            path8765797Path.reset();
            path8765797Path.moveTo(574.05f, 433.28f);
            path8765797Path.lineTo(631.78f, 433.28f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765797Path, paint);
            canvas.restore();

            // path876-70-9-2-2
            RectF path87670922Rect = CacheForCanvas1.path87670922Rect;
            path87670922Rect.set(589.05f, 459.26f, 646.78f, 459.26f);
            Path path87670922Path = CacheForCanvas1.path87670922Path;
            path87670922Path.reset();
            path87670922Path.moveTo(589.05f, 459.26f);
            path87670922Path.lineTo(646.78f, 459.26f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87670922Path, paint);
            canvas.restore();

            // path876-3-9-2-6-4
            RectF path87639264Rect = CacheForCanvas1.path87639264Rect;
            path87639264Rect.set(591.55f, 463.59f, 649.28f, 463.59f);
            Path path87639264Path = CacheForCanvas1.path87639264Path;
            path87639264Path.reset();
            path87639264Path.moveTo(591.55f, 463.59f);
            path87639264Path.lineTo(649.28f, 463.59f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87639264Path, paint);
            canvas.restore();

            // path876-6-3-0-6-0
            RectF path87663060Rect = CacheForCanvas1.path87663060Rect;
            path87663060Rect.set(594.05f, 467.92f, 651.78f, 467.92f);
            Path path87663060Path = CacheForCanvas1.path87663060Path;
            path87663060Path.reset();
            path87663060Path.moveTo(594.05f, 467.92f);
            path87663060Path.lineTo(651.78f, 467.92f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87663060Path, paint);
            canvas.restore();

            // path876-7-6-2-4-6
            RectF path87676246Rect = CacheForCanvas1.path87676246Rect;
            path87676246Rect.set(596.55f, 472.25f, 654.28f, 472.25f);
            Path path87676246Path = CacheForCanvas1.path87676246Path;
            path87676246Path.reset();
            path87676246Path.moveTo(596.55f, 472.25f);
            path87676246Path.lineTo(654.28f, 472.25f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87676246Path, paint);
            canvas.restore();

            // path876-5-0-3-9-2
            RectF path87650392Rect = CacheForCanvas1.path87650392Rect;
            path87650392Rect.set(586.55f, 454.93f, 644.28f, 454.93f);
            Path path87650392Path = CacheForCanvas1.path87650392Path;
            path87650392Path.reset();
            path87650392Path.moveTo(586.55f, 454.93f);
            path87650392Path.lineTo(644.28f, 454.93f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87650392Path, paint);
            canvas.restore();

            // rect1196-5-9
            RectF rect119659Rect = CacheForCanvas1.rect119659Rect;
            rect119659Rect.set(543.27f, 389.97f, 596.55f, 472.25f);
            Path rect119659Path = CacheForCanvas1.rect119659Path;
            rect119659Path.reset();
            rect119659Path.moveTo(549.05f, 389.97f);
            rect119659Path.lineTo(543.27f, 389.97f);
            rect119659Path.lineTo(590.77f, 472.25f);
            rect119659Path.lineTo(596.55f, 472.25f);
            rect119659Path.lineTo(549.05f, 389.97f);
            rect119659Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor4);
            canvas.drawPath(rect119659Path, paint);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.07f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(rect119659Path, paint);
            canvas.restore();

            // path876-1-9
            RectF path87619Rect = CacheForCanvas1.path87619Rect;
            path87619Rect.set(464.94f, 394.3f, 545.77f, 394.31f);
            Path path87619Path = CacheForCanvas1.path87619Path;
            path87619Path.reset();
            path87619Path.moveTo(545.77f, 394.31f);
            path87619Path.lineTo(464.94f, 394.31f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87619Path, paint);
            canvas.restore();

            // path876-3-1-0
            RectF path876310Rect = CacheForCanvas1.path876310Rect;
            path876310Rect.set(467.44f, 398.63f, 548.27f, 398.64f);
            Path path876310Path = CacheForCanvas1.path876310Path;
            path876310Path.reset();
            path876310Path.moveTo(548.27f, 398.64f);
            path876310Path.lineTo(467.44f, 398.64f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876310Path, paint);
            canvas.restore();

            // path876-6-5-81
            RectF path8766581Rect = CacheForCanvas1.path8766581Rect;
            path8766581Rect.set(469.94f, 402.96f, 550.77f, 402.97f);
            Path path8766581Path = CacheForCanvas1.path8766581Path;
            path8766581Path.reset();
            path8766581Path.moveTo(550.77f, 402.97f);
            path8766581Path.lineTo(469.94f, 402.97f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766581Path, paint);
            canvas.restore();

            // path876-7-9-31
            RectF path8767931Rect = CacheForCanvas1.path8767931Rect;
            path8767931Rect.set(472.44f, 407.29f, 553.27f, 407.3f);
            Path path8767931Path = CacheForCanvas1.path8767931Path;
            path8767931Path.reset();
            path8767931Path.moveTo(553.27f, 407.3f);
            path8767931Path.lineTo(472.44f, 407.3f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767931Path, paint);
            canvas.restore();

            // path876-5-77-10
            RectF path87657710Rect = CacheForCanvas1.path87657710Rect;
            path87657710Rect.set(462.44f, 389.97f, 543.27f, 389.98f);
            Path path87657710Path = CacheForCanvas1.path87657710Path;
            path87657710Path.reset();
            path87657710Path.moveTo(543.27f, 389.98f);
            path87657710Path.lineTo(462.44f, 389.98f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87657710Path, paint);
            canvas.restore();

            // path876-70-6-3
            RectF path8767063Rect = CacheForCanvas1.path8767063Rect;
            path8767063Rect.set(477.44f, 415.96f, 558.27f, 415.96f);
            Path path8767063Path = CacheForCanvas1.path8767063Path;
            path8767063Path.reset();
            path8767063Path.moveTo(558.27f, 415.96f);
            path8767063Path.lineTo(477.44f, 415.96f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767063Path, paint);
            canvas.restore();

            // path876-3-9-7-4
            RectF path8763974Rect = CacheForCanvas1.path8763974Rect;
            path8763974Rect.set(479.94f, 420.29f, 560.77f, 420.29f);
            Path path8763974Path = CacheForCanvas1.path8763974Path;
            path8763974Path.reset();
            path8763974Path.moveTo(560.77f, 420.29f);
            path8763974Path.lineTo(479.94f, 420.29f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763974Path, paint);
            canvas.restore();

            // path876-6-3-3-0
            RectF path8766330Rect = CacheForCanvas1.path8766330Rect;
            path8766330Rect.set(482.44f, 424.62f, 563.27f, 424.62f);
            Path path8766330Path = CacheForCanvas1.path8766330Path;
            path8766330Path.reset();
            path8766330Path.moveTo(563.27f, 424.62f);
            path8766330Path.lineTo(482.44f, 424.62f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766330Path, paint);
            canvas.restore();

            // path876-7-6-6-3
            RectF path8767663Rect = CacheForCanvas1.path8767663Rect;
            path8767663Rect.set(484.94f, 428.95f, 565.77f, 428.95f);
            Path path8767663Path = CacheForCanvas1.path8767663Path;
            path8767663Path.reset();
            path8767663Path.moveTo(565.77f, 428.95f);
            path8767663Path.lineTo(484.94f, 428.95f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767663Path, paint);
            canvas.restore();

            // path876-5-0-5-9
            RectF path8765059Rect = CacheForCanvas1.path8765059Rect;
            path8765059Rect.set(474.94f, 411.62f, 555.77f, 411.63f);
            Path path8765059Path = CacheForCanvas1.path8765059Path;
            path8765059Path.reset();
            path8765059Path.moveTo(555.77f, 411.63f);
            path8765059Path.lineTo(474.94f, 411.63f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765059Path, paint);
            canvas.restore();

            // path876-62-6-1
            RectF path8766261Rect = CacheForCanvas1.path8766261Rect;
            path8766261Rect.set(489.94f, 437.61f, 570.77f, 437.61f);
            Path path8766261Path = CacheForCanvas1.path8766261Path;
            path8766261Path.reset();
            path8766261Path.moveTo(570.77f, 437.61f);
            path8766261Path.lineTo(489.94f, 437.61f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766261Path, paint);
            canvas.restore();

            // path876-3-6-3-9
            RectF path8763639Rect = CacheForCanvas1.path8763639Rect;
            path8763639Rect.set(492.44f, 441.94f, 573.27f, 441.94f);
            Path path8763639Path = CacheForCanvas1.path8763639Path;
            path8763639Path.reset();
            path8763639Path.moveTo(573.27f, 441.94f);
            path8763639Path.lineTo(492.44f, 441.94f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763639Path, paint);
            canvas.restore();

            // path876-6-1-9-6
            RectF path8766196Rect = CacheForCanvas1.path8766196Rect;
            path8766196Rect.set(494.94f, 446.27f, 575.77f, 446.27f);
            Path path8766196Path = CacheForCanvas1.path8766196Path;
            path8766196Path.reset();
            path8766196Path.moveTo(575.77f, 446.27f);
            path8766196Path.lineTo(494.94f, 446.27f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766196Path, paint);
            canvas.restore();

            // path876-7-8-4-9
            RectF path8767849Rect = CacheForCanvas1.path8767849Rect;
            path8767849Rect.set(497.44f, 450.6f, 578.27f, 450.6f);
            Path path8767849Path = CacheForCanvas1.path8767849Path;
            path8767849Path.reset();
            path8767849Path.moveTo(578.27f, 450.6f);
            path8767849Path.lineTo(497.44f, 450.6f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767849Path, paint);
            canvas.restore();

            // path876-5-7-8-3
            RectF path8765783Rect = CacheForCanvas1.path8765783Rect;
            path8765783Rect.set(487.44f, 433.28f, 568.27f, 433.28f);
            Path path8765783Path = CacheForCanvas1.path8765783Path;
            path8765783Path.reset();
            path8765783Path.moveTo(568.27f, 433.28f);
            path8765783Path.lineTo(487.44f, 433.28f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765783Path, paint);
            canvas.restore();

            // path876-70-9-1-3
            RectF path87670913Rect = CacheForCanvas1.path87670913Rect;
            path87670913Rect.set(502.44f, 459.26f, 583.27f, 459.26f);
            Path path87670913Path = CacheForCanvas1.path87670913Path;
            path87670913Path.reset();
            path87670913Path.moveTo(583.27f, 459.26f);
            path87670913Path.lineTo(502.44f, 459.26f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87670913Path, paint);
            canvas.restore();

            // path876-3-9-2-2-80
            RectF path876392280Rect = CacheForCanvas1.path876392280Rect;
            path876392280Rect.set(504.94f, 463.59f, 585.77f, 463.59f);
            Path path876392280Path = CacheForCanvas1.path876392280Path;
            path876392280Path.reset();
            path876392280Path.moveTo(585.77f, 463.59f);
            path876392280Path.lineTo(504.94f, 463.59f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876392280Path, paint);
            canvas.restore();

            // path876-6-3-0-9-5
            RectF path87663095Rect = CacheForCanvas1.path87663095Rect;
            path87663095Rect.set(507.44f, 467.92f, 588.27f, 467.92f);
            Path path87663095Path = CacheForCanvas1.path87663095Path;
            path87663095Path.reset();
            path87663095Path.moveTo(588.27f, 467.92f);
            path87663095Path.lineTo(507.44f, 467.92f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87663095Path, paint);
            canvas.restore();

            // path876-7-6-2-3-6
            RectF path87676236Rect = CacheForCanvas1.path87676236Rect;
            path87676236Rect.set(509.94f, 472.25f, 590.77f, 472.25f);
            Path path87676236Path = CacheForCanvas1.path87676236Path;
            path87676236Path.reset();
            path87676236Path.moveTo(590.77f, 472.25f);
            path87676236Path.lineTo(509.94f, 472.25f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87676236Path, paint);
            canvas.restore();

            // path876-5-0-3-90-6
            RectF path876503906Rect = CacheForCanvas1.path876503906Rect;
            path876503906Rect.set(499.94f, 454.93f, 580.77f, 454.93f);
            Path path876503906Path = CacheForCanvas1.path876503906Path;
            path876503906Path.reset();
            path876503906Path.moveTo(580.77f, 454.93f);
            path876503906Path.lineTo(499.94f, 454.93f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876503906Path, paint);
            canvas.restore();

            // rect1446-4
            RectF rect14464Rect = CacheForCanvas1.rect14464Rect;
            rect14464Rect.set(389.76f, 234.09f, 472.5f, 277.39f);
            Path rect14464Path = CacheForCanvas1.rect14464Path;
            rect14464Path.reset();
            rect14464Path.moveTo(389.76f, 234.09f);
            rect14464Path.lineTo(447.5f, 234.09f);
            rect14464Path.lineTo(472.5f, 277.39f);
            rect14464Path.lineTo(414.76f, 277.39f);
            rect14464Path.lineTo(389.76f, 234.09f);
            rect14464Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor4);
            canvas.drawPath(rect14464Path, paint);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.07f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(rect14464Path, paint);
            canvas.restore();

            // rect2182
            RectF rect2182Rect = CacheForCanvas1.rect2182Rect;
            rect2182Rect.set(173.93f, 0.26f, 441.78f, 104.19f);
            Path rect2182Path = CacheForCanvas1.rect2182Path;
            rect2182Path.reset();
            rect2182Path.moveTo(173.93f, 0.26f);
            rect2182Path.lineTo(381.78f, 0.26f);
            rect2182Path.lineTo(441.78f, 104.19f);
            rect2182Path.lineTo(233.93f, 104.19f);
            rect2182Path.lineTo(173.93f, 0.26f);
            rect2182Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor5);
            canvas.drawPath(rect2182Path, paint);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(4.03f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(rect2182Path, paint);
            canvas.restore();

            // rect2178
            RectF rect2178Rect = CacheForCanvas1.rect2178Rect;
            rect2178Rect.set(84.18f, 164.81f, 373.93f, 346.67f);
            Path rect2178Path = CacheForCanvas1.rect2178Path;
            rect2178Path.reset();
            rect2178Path.moveTo(84.18f, 164.81f);
            rect2178Path.lineTo(268.93f, 164.81f);
            rect2178Path.lineTo(373.93f, 346.67f);
            rect2178Path.lineTo(189.18f, 346.67f);
            rect2178Path.lineTo(84.18f, 164.81f);
            rect2178Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor5);
            canvas.drawPath(rect2178Path, paint);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(4.03f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(rect2178Path, paint);
            canvas.restore();

            // path828-6
            RectF path8286Rect = CacheForCanvas1.path8286Rect;
            path8286Rect.set(30.26f, 1124.43f, 677.29f, 1635.43f);
            Path path8286Path = CacheForCanvas1.path8286Path;
            path8286Path.reset();
            path8286Path.moveTo(677.29f, 1635.43f);
            path8286Path.lineTo(469.44f, 1635.43f);
            path8286Path.lineTo(429.44f, 1566.15f);
            path8286Path.lineTo(244.69f, 1566.15f);
            path8286Path.lineTo(168.38f, 1433.98f);
            path8286Path.moveTo(97.84f, 1311.87f);
            path8286Path.lineTo(30.26f, 1193.71f);
            path8286Path.lineTo(215.01f, 1193.71f);
            path8286Path.lineTo(175.01f, 1124.43f);
            path8286Path.lineTo(382.86f, 1124.43f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8286Path, paint);
            canvas.restore();

            // path830-2
            RectF path8302Rect = CacheForCanvas1.path8302Rect;
            path8302Rect.set(394.44f, 1505.53f, 429.44f, 1566.15f);
            Path path8302Path = CacheForCanvas1.path8302Path;
            path8302Path.reset();
            path8302Path.moveTo(394.44f, 1505.53f);
            path8302Path.lineTo(429.44f, 1566.15f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8302Path, paint);
            canvas.restore();

            // path842-3
            RectF path8423Rect = CacheForCanvas1.path8423Rect;
            path8423Rect.set(214.44f, 1193.76f, 249.44f, 1254.38f);
            Path path8423Path = CacheForCanvas1.path8423Path;
            path8423Path.reset();
            path8423Path.moveTo(249.44f, 1254.38f);
            path8423Path.lineTo(214.44f, 1193.76f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8423Path, paint);
            canvas.restore();

            // path844-6
            RectF path8446Rect = CacheForCanvas1.path8446Rect;
            path8446Rect.set(382.29f, 1124.48f, 492.29f, 1315f);
            Path path8446Path = CacheForCanvas1.path8446Path;
            path8446Path.reset();
            path8446Path.moveTo(492.29f, 1315f);
            path8446Path.lineTo(382.29f, 1124.48f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8446Path, paint);
            canvas.restore();

            // path846-0
            RectF path8460Rect = CacheForCanvas1.path8460Rect;
            path8460Rect.set(492.29f, 1315f, 677.29f, 1635.43f);
            Path path8460Path = CacheForCanvas1.path8460Path;
            path8460Path.reset();
            path8460Path.moveTo(492.29f, 1315f);
            path8460Path.lineTo(677.29f, 1635.43f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8460Path, paint);
            canvas.restore();

            // path876- 2
            RectF path8762Rect = CacheForCanvas1.path8762Rect;
            path8762Rect.set(401.94f, 1518.52f, 459.67f, 1518.52f);
            Path path8762Path = CacheForCanvas1.path8762Path;
            path8762Path.reset();
            path8762Path.moveTo(459.67f, 1518.52f);
            path8762Path.lineTo(401.94f, 1518.52f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8762Path, paint);
            canvas.restore();

            // path876-3- 3
            RectF path87633Rect = CacheForCanvas1.path87633Rect;
            path87633Rect.set(404.44f, 1522.85f, 462.17f, 1522.85f);
            Path path87633Path = CacheForCanvas1.path87633Path;
            path87633Path.reset();
            path87633Path.moveTo(462.17f, 1522.85f);
            path87633Path.lineTo(404.44f, 1522.85f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87633Path, paint);
            canvas.restore();

            // path876-6-9
            RectF path87669Rect = CacheForCanvas1.path87669Rect;
            path87669Rect.set(406.94f, 1527.18f, 464.67f, 1527.18f);
            Path path87669Path = CacheForCanvas1.path87669Path;
            path87669Path.reset();
            path87669Path.moveTo(464.67f, 1527.18f);
            path87669Path.lineTo(406.94f, 1527.18f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87669Path, paint);
            canvas.restore();

            // path876-7-43
            RectF path876743Rect = CacheForCanvas1.path876743Rect;
            path876743Rect.set(409.44f, 1531.51f, 467.17f, 1531.51f);
            Path path876743Path = CacheForCanvas1.path876743Path;
            path876743Path.reset();
            path876743Path.moveTo(467.17f, 1531.51f);
            path876743Path.lineTo(409.44f, 1531.51f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876743Path, paint);
            canvas.restore();

            // path876-5-5
            RectF path87655Rect = CacheForCanvas1.path87655Rect;
            path87655Rect.set(399.44f, 1514.19f, 457.17f, 1514.19f);
            Path path87655Path = CacheForCanvas1.path87655Path;
            path87655Path.reset();
            path87655Path.moveTo(457.17f, 1514.19f);
            path87655Path.lineTo(399.44f, 1514.19f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87655Path, paint);
            canvas.restore();

            // path876-70-1
            RectF path876701Rect = CacheForCanvas1.path876701Rect;
            path876701Rect.set(414.44f, 1540.17f, 472.17f, 1540.17f);
            Path path876701Path = CacheForCanvas1.path876701Path;
            path876701Path.reset();
            path876701Path.moveTo(472.17f, 1540.17f);
            path876701Path.lineTo(414.44f, 1540.17f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876701Path, paint);
            canvas.restore();

            // path876-3-9- 4
            RectF path876394Rect = CacheForCanvas1.path876394Rect;
            path876394Rect.set(416.94f, 1544.5f, 474.67f, 1544.5f);
            Path path876394Path = CacheForCanvas1.path876394Path;
            path876394Path.reset();
            path876394Path.moveTo(474.67f, 1544.5f);
            path876394Path.lineTo(416.94f, 1544.5f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876394Path, paint);
            canvas.restore();

            // path876-6-3-31
            RectF path8766331Rect = CacheForCanvas1.path8766331Rect;
            path8766331Rect.set(419.44f, 1548.83f, 477.17f, 1548.83f);
            Path path8766331Path = CacheForCanvas1.path8766331Path;
            path8766331Path.reset();
            path8766331Path.moveTo(477.17f, 1548.83f);
            path8766331Path.lineTo(419.44f, 1548.83f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766331Path, paint);
            canvas.restore();

            // path876-7-6-46
            RectF path8767646Rect = CacheForCanvas1.path8767646Rect;
            path8767646Rect.set(421.94f, 1553.16f, 479.67f, 1553.16f);
            Path path8767646Path = CacheForCanvas1.path8767646Path;
            path8767646Path.reset();
            path8767646Path.moveTo(479.67f, 1553.16f);
            path8767646Path.lineTo(421.94f, 1553.16f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767646Path, paint);
            canvas.restore();

            // path876-5-0-94
            RectF path8765094Rect = CacheForCanvas1.path8765094Rect;
            path8765094Rect.set(411.94f, 1535.84f, 469.67f, 1535.84f);
            Path path8765094Path = CacheForCanvas1.path8765094Path;
            path8765094Path.reset();
            path8765094Path.moveTo(469.67f, 1535.84f);
            path8765094Path.lineTo(411.94f, 1535.84f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765094Path, paint);
            canvas.restore();

            // path876-62-22
            RectF path8766222Rect = CacheForCanvas1.path8766222Rect;
            path8766222Rect.set(426.94f, 1561.82f, 484.67f, 1561.82f);
            Path path8766222Path = CacheForCanvas1.path8766222Path;
            path8766222Path.reset();
            path8766222Path.moveTo(484.67f, 1561.82f);
            path8766222Path.lineTo(426.94f, 1561.82f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766222Path, paint);
            canvas.restore();

            // path876-3-6-6
            RectF path876366Rect = CacheForCanvas1.path876366Rect;
            path876366Rect.set(429.44f, 1566.15f, 487.17f, 1566.15f);
            Path path876366Path = CacheForCanvas1.path876366Path;
            path876366Path.reset();
            path876366Path.moveTo(487.17f, 1566.15f);
            path876366Path.lineTo(429.44f, 1566.15f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876366Path, paint);
            canvas.restore();

            // path876-6-1-4
            RectF path876614Rect = CacheForCanvas1.path876614Rect;
            path876614Rect.set(431.94f, 1570.48f, 489.67f, 1570.48f);
            Path path876614Path = CacheForCanvas1.path876614Path;
            path876614Path.reset();
            path876614Path.moveTo(489.67f, 1570.48f);
            path876614Path.lineTo(431.94f, 1570.48f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876614Path, paint);
            canvas.restore();

            // path876-7-8-1
            RectF path876781Rect = CacheForCanvas1.path876781Rect;
            path876781Rect.set(434.44f, 1574.81f, 492.17f, 1574.81f);
            Path path876781Path = CacheForCanvas1.path876781Path;
            path876781Path.reset();
            path876781Path.moveTo(492.17f, 1574.81f);
            path876781Path.lineTo(434.44f, 1574.81f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876781Path, paint);
            canvas.restore();

            // path876-5-7-2
            RectF path876572Rect = CacheForCanvas1.path876572Rect;
            path876572Rect.set(424.44f, 1557.49f, 482.17f, 1557.49f);
            Path path876572Path = CacheForCanvas1.path876572Path;
            path876572Path.reset();
            path876572Path.moveTo(482.17f, 1557.49f);
            path876572Path.lineTo(424.44f, 1557.49f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876572Path, paint);
            canvas.restore();

            // path876-70-9-8
            RectF path8767098Rect = CacheForCanvas1.path8767098Rect;
            path8767098Rect.set(439.44f, 1583.47f, 497.17f, 1583.47f);
            Path path8767098Path = CacheForCanvas1.path8767098Path;
            path8767098Path.reset();
            path8767098Path.moveTo(497.17f, 1583.47f);
            path8767098Path.lineTo(439.44f, 1583.47f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767098Path, paint);
            canvas.restore();

            // path876-3-9-2-89
            RectF path87639289Rect = CacheForCanvas1.path87639289Rect;
            path87639289Rect.set(441.94f, 1587.8f, 499.67f, 1587.8f);
            Path path87639289Path = CacheForCanvas1.path87639289Path;
            path87639289Path.reset();
            path87639289Path.moveTo(499.67f, 1587.8f);
            path87639289Path.lineTo(441.94f, 1587.8f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87639289Path, paint);
            canvas.restore();

            // path876-6-3-0-2
            RectF path8766302Rect = CacheForCanvas1.path8766302Rect;
            path8766302Rect.set(444.44f, 1592.13f, 502.17f, 1592.13f);
            Path path8766302Path = CacheForCanvas1.path8766302Path;
            path8766302Path.reset();
            path8766302Path.moveTo(502.17f, 1592.13f);
            path8766302Path.lineTo(444.44f, 1592.13f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766302Path, paint);
            canvas.restore();

            // path876-7-6-2-8
            RectF path8767628Rect = CacheForCanvas1.path8767628Rect;
            path8767628Rect.set(446.94f, 1596.46f, 504.67f, 1596.46f);
            Path path8767628Path = CacheForCanvas1.path8767628Path;
            path8767628Path.reset();
            path8767628Path.moveTo(504.67f, 1596.46f);
            path8767628Path.lineTo(446.94f, 1596.46f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767628Path, paint);
            canvas.restore();

            // path876-5-0-3-8
            RectF path8765038Rect = CacheForCanvas1.path8765038Rect;
            path8765038Rect.set(436.94f, 1579.14f, 494.67f, 1579.14f);
            Path path8765038Path = CacheForCanvas1.path8765038Path;
            path8765038Path.reset();
            path8765038Path.moveTo(494.67f, 1579.14f);
            path8765038Path.lineTo(436.94f, 1579.14f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765038Path, paint);
            canvas.restore();

            // rect1196-8
            RectF rect11968Rect = CacheForCanvas1.rect11968Rect;
            rect11968Rect.set(457.17f, 1514.19f, 510.45f, 1596.46f);
            Path rect11968Path = CacheForCanvas1.rect11968Path;
            rect11968Path.reset();
            rect11968Path.moveTo(457.17f, 1514.19f);
            rect11968Path.lineTo(462.95f, 1514.19f);
            rect11968Path.lineTo(510.45f, 1596.46f);
            rect11968Path.lineTo(504.67f, 1596.46f);
            rect11968Path.lineTo(457.17f, 1514.19f);
            rect11968Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor4);
            canvas.drawPath(rect11968Path, paint);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.07f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(rect11968Path, paint);
            canvas.restore();

            // path876-30-6
            RectF path876306Rect = CacheForCanvas1.path876306Rect;
            path876306Rect.set(552.05f, 1518.52f, 609.79f, 1518.52f);
            Path path876306Path = CacheForCanvas1.path876306Path;
            path876306Path.reset();
            path876306Path.moveTo(552.05f, 1518.52f);
            path876306Path.lineTo(609.79f, 1518.52f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876306Path, paint);
            canvas.restore();

            // path876-3-7-8
            RectF path876378Rect = CacheForCanvas1.path876378Rect;
            path876378Rect.set(554.55f, 1522.85f, 612.29f, 1522.85f);
            Path path876378Path = CacheForCanvas1.path876378Path;
            path876378Path.reset();
            path876378Path.moveTo(554.55f, 1522.85f);
            path876378Path.lineTo(612.29f, 1522.85f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876378Path, paint);
            canvas.restore();

            // path876-6-8-38
            RectF path8766838Rect = CacheForCanvas1.path8766838Rect;
            path8766838Rect.set(557.05f, 1527.18f, 614.79f, 1527.18f);
            Path path8766838Path = CacheForCanvas1.path8766838Path;
            path8766838Path.reset();
            path8766838Path.moveTo(557.05f, 1527.18f);
            path8766838Path.lineTo(614.79f, 1527.18f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766838Path, paint);
            canvas.restore();

            // path876-7-68-3
            RectF path8767683Rect = CacheForCanvas1.path8767683Rect;
            path8767683Rect.set(559.55f, 1531.51f, 617.29f, 1531.51f);
            Path path8767683Path = CacheForCanvas1.path8767683Path;
            path8767683Path.reset();
            path8767683Path.moveTo(559.55f, 1531.51f);
            path8767683Path.lineTo(617.29f, 1531.51f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767683Path, paint);
            canvas.restore();

            // path876-5-8-3
            RectF path876583Rect = CacheForCanvas1.path876583Rect;
            path876583Rect.set(549.55f, 1514.19f, 607.29f, 1514.19f);
            Path path876583Path = CacheForCanvas1.path876583Path;
            path876583Path.reset();
            path876583Path.moveTo(549.55f, 1514.19f);
            path876583Path.lineTo(607.29f, 1514.19f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876583Path, paint);
            canvas.restore();

            // path876-70-4-3
            RectF path8767043Rect = CacheForCanvas1.path8767043Rect;
            path8767043Rect.set(564.55f, 1540.17f, 622.29f, 1540.17f);
            Path path8767043Path = CacheForCanvas1.path8767043Path;
            path8767043Path.reset();
            path8767043Path.moveTo(564.55f, 1540.17f);
            path8767043Path.lineTo(622.29f, 1540.17f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767043Path, paint);
            canvas.restore();

            // path876-3-9-3-8
            RectF path8763938Rect = CacheForCanvas1.path8763938Rect;
            path8763938Rect.set(567.05f, 1544.5f, 624.79f, 1544.5f);
            Path path8763938Path = CacheForCanvas1.path8763938Path;
            path8763938Path.reset();
            path8763938Path.moveTo(567.05f, 1544.5f);
            path8763938Path.lineTo(624.79f, 1544.5f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763938Path, paint);
            canvas.restore();

            // path876-6-3-1-0
            RectF path8766310Rect = CacheForCanvas1.path8766310Rect;
            path8766310Rect.set(569.55f, 1548.83f, 627.29f, 1548.83f);
            Path path8766310Path = CacheForCanvas1.path8766310Path;
            path8766310Path.reset();
            path8766310Path.moveTo(569.55f, 1548.83f);
            path8766310Path.lineTo(627.29f, 1548.83f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766310Path, paint);
            canvas.restore();

            // path876-7-6-4-4
            RectF path8767644Rect = CacheForCanvas1.path8767644Rect;
            path8767644Rect.set(572.05f, 1553.16f, 629.79f, 1553.16f);
            Path path8767644Path = CacheForCanvas1.path8767644Path;
            path8767644Path.reset();
            path8767644Path.moveTo(572.05f, 1553.16f);
            path8767644Path.lineTo(629.79f, 1553.16f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767644Path, paint);
            canvas.restore();

            // path876-5-0-9-7
            RectF path8765097Rect = CacheForCanvas1.path8765097Rect;
            path8765097Rect.set(562.05f, 1535.84f, 619.79f, 1535.84f);
            Path path8765097Path = CacheForCanvas1.path8765097Path;
            path8765097Path.reset();
            path8765097Path.moveTo(562.05f, 1535.84f);
            path8765097Path.lineTo(619.79f, 1535.84f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765097Path, paint);
            canvas.restore();

            // path876-62-2-68
            RectF path87662268Rect = CacheForCanvas1.path87662268Rect;
            path87662268Rect.set(577.05f, 1561.82f, 634.79f, 1561.82f);
            Path path87662268Path = CacheForCanvas1.path87662268Path;
            path87662268Path.reset();
            path87662268Path.moveTo(577.05f, 1561.82f);
            path87662268Path.lineTo(634.79f, 1561.82f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87662268Path, paint);
            canvas.restore();

            // path876-3-6-0-9
            RectF path8763609Rect = CacheForCanvas1.path8763609Rect;
            path8763609Rect.set(579.55f, 1566.15f, 637.29f, 1566.15f);
            Path path8763609Path = CacheForCanvas1.path8763609Path;
            path8763609Path.reset();
            path8763609Path.moveTo(579.55f, 1566.15f);
            path8763609Path.lineTo(637.29f, 1566.15f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763609Path, paint);
            canvas.restore();

            // path876-6-1-6-0
            RectF path8766160Rect = CacheForCanvas1.path8766160Rect;
            path8766160Rect.set(582.05f, 1570.48f, 639.79f, 1570.48f);
            Path path8766160Path = CacheForCanvas1.path8766160Path;
            path8766160Path.reset();
            path8766160Path.moveTo(582.05f, 1570.48f);
            path8766160Path.lineTo(639.79f, 1570.48f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766160Path, paint);
            canvas.restore();

            // path876-7-8-8-6
            RectF path8767886Rect = CacheForCanvas1.path8767886Rect;
            path8767886Rect.set(584.55f, 1574.81f, 642.29f, 1574.81f);
            Path path8767886Path = CacheForCanvas1.path8767886Path;
            path8767886Path.reset();
            path8767886Path.moveTo(584.55f, 1574.81f);
            path8767886Path.lineTo(642.29f, 1574.81f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767886Path, paint);
            canvas.restore();

            // path876-5-7-9-8
            RectF path8765798Rect = CacheForCanvas1.path8765798Rect;
            path8765798Rect.set(574.55f, 1557.49f, 632.29f, 1557.49f);
            Path path8765798Path = CacheForCanvas1.path8765798Path;
            path8765798Path.reset();
            path8765798Path.moveTo(574.55f, 1557.49f);
            path8765798Path.lineTo(632.29f, 1557.49f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765798Path, paint);
            canvas.restore();

            // path876-70-9-2-7
            RectF path87670927Rect = CacheForCanvas1.path87670927Rect;
            path87670927Rect.set(589.55f, 1583.47f, 647.29f, 1583.47f);
            Path path87670927Path = CacheForCanvas1.path87670927Path;
            path87670927Path.reset();
            path87670927Path.moveTo(589.55f, 1583.47f);
            path87670927Path.lineTo(647.29f, 1583.47f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87670927Path, paint);
            canvas.restore();

            // path876-3-9-2-6-9
            RectF path87639269Rect = CacheForCanvas1.path87639269Rect;
            path87639269Rect.set(592.05f, 1587.8f, 649.79f, 1587.8f);
            Path path87639269Path = CacheForCanvas1.path87639269Path;
            path87639269Path.reset();
            path87639269Path.moveTo(592.05f, 1587.8f);
            path87639269Path.lineTo(649.79f, 1587.8f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87639269Path, paint);
            canvas.restore();

            // path876-6-3-0-6-03
            RectF path876630603Rect = CacheForCanvas1.path876630603Rect;
            path876630603Rect.set(594.55f, 1592.13f, 652.29f, 1592.13f);
            Path path876630603Path = CacheForCanvas1.path876630603Path;
            path876630603Path.reset();
            path876630603Path.moveTo(594.55f, 1592.13f);
            path876630603Path.lineTo(652.29f, 1592.13f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876630603Path, paint);
            canvas.restore();

            // path876-7-6-2-4-3
            RectF path87676243Rect = CacheForCanvas1.path87676243Rect;
            path87676243Rect.set(597.05f, 1596.46f, 654.79f, 1596.46f);
            Path path87676243Path = CacheForCanvas1.path87676243Path;
            path87676243Path.reset();
            path87676243Path.moveTo(597.05f, 1596.46f);
            path87676243Path.lineTo(654.79f, 1596.46f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87676243Path, paint);
            canvas.restore();

            // path876-5-0-3-9-3
            RectF path87650393Rect = CacheForCanvas1.path87650393Rect;
            path87650393Rect.set(587.05f, 1579.14f, 644.79f, 1579.14f);
            Path path87650393Path = CacheForCanvas1.path87650393Path;
            path87650393Path.reset();
            path87650393Path.moveTo(587.05f, 1579.14f);
            path87650393Path.lineTo(644.79f, 1579.14f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87650393Path, paint);
            canvas.restore();

            // rect1196-5-7
            RectF rect119657Rect = CacheForCanvas1.rect119657Rect;
            rect119657Rect.set(543.78f, 1514.19f, 597.05f, 1596.46f);
            Path rect119657Path = CacheForCanvas1.rect119657Path;
            rect119657Path.reset();
            rect119657Path.moveTo(549.55f, 1514.19f);
            rect119657Path.lineTo(543.78f, 1514.19f);
            rect119657Path.lineTo(591.28f, 1596.46f);
            rect119657Path.lineTo(597.05f, 1596.46f);
            rect119657Path.lineTo(549.55f, 1514.19f);
            rect119657Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor4);
            canvas.drawPath(rect119657Path, paint);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.07f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(rect119657Path, paint);
            canvas.restore();

            // path876-1-32
            RectF path876132Rect = CacheForCanvas1.path876132Rect;
            path876132Rect.set(465.45f, 1518.52f, 546.28f, 1518.52f);
            Path path876132Path = CacheForCanvas1.path876132Path;
            path876132Path.reset();
            path876132Path.moveTo(546.28f, 1518.52f);
            path876132Path.lineTo(465.45f, 1518.52f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876132Path, paint);
            canvas.restore();

            // path876-3-1-6
            RectF path876316Rect = CacheForCanvas1.path876316Rect;
            path876316Rect.set(467.95f, 1522.85f, 548.78f, 1522.85f);
            Path path876316Path = CacheForCanvas1.path876316Path;
            path876316Path.reset();
            path876316Path.moveTo(548.78f, 1522.85f);
            path876316Path.lineTo(467.95f, 1522.85f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876316Path, paint);
            canvas.restore();

            // path876-6-5-5
            RectF path876655Rect = CacheForCanvas1.path876655Rect;
            path876655Rect.set(470.45f, 1527.18f, 551.28f, 1527.18f);
            Path path876655Path = CacheForCanvas1.path876655Path;
            path876655Path.reset();
            path876655Path.moveTo(551.28f, 1527.18f);
            path876655Path.lineTo(470.45f, 1527.18f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876655Path, paint);
            canvas.restore();

            // path876-7-9-2
            RectF path876792Rect = CacheForCanvas1.path876792Rect;
            path876792Rect.set(472.95f, 1531.51f, 553.78f, 1531.51f);
            Path path876792Path = CacheForCanvas1.path876792Path;
            path876792Path.reset();
            path876792Path.moveTo(553.78f, 1531.51f);
            path876792Path.lineTo(472.95f, 1531.51f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876792Path, paint);
            canvas.restore();

            // path876-5-77-6
            RectF path8765776Rect = CacheForCanvas1.path8765776Rect;
            path8765776Rect.set(462.95f, 1514.19f, 543.78f, 1514.19f);
            Path path8765776Path = CacheForCanvas1.path8765776Path;
            path8765776Path.reset();
            path8765776Path.moveTo(543.78f, 1514.19f);
            path8765776Path.lineTo(462.95f, 1514.19f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765776Path, paint);
            canvas.restore();

            // path876-70-6-5
            RectF path8767065Rect = CacheForCanvas1.path8767065Rect;
            path8767065Rect.set(477.95f, 1540.17f, 558.78f, 1540.17f);
            Path path8767065Path = CacheForCanvas1.path8767065Path;
            path8767065Path.reset();
            path8767065Path.moveTo(558.78f, 1540.17f);
            path8767065Path.lineTo(477.95f, 1540.17f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767065Path, paint);
            canvas.restore();

            // path876-3-9-7-8
            RectF path8763978Rect = CacheForCanvas1.path8763978Rect;
            path8763978Rect.set(480.45f, 1544.5f, 561.28f, 1544.5f);
            Path path8763978Path = CacheForCanvas1.path8763978Path;
            path8763978Path.reset();
            path8763978Path.moveTo(561.28f, 1544.5f);
            path8763978Path.lineTo(480.45f, 1544.5f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763978Path, paint);
            canvas.restore();

            // path876-6-3-3-7
            RectF path8766337Rect = CacheForCanvas1.path8766337Rect;
            path8766337Rect.set(482.95f, 1548.83f, 563.78f, 1548.83f);
            Path path8766337Path = CacheForCanvas1.path8766337Path;
            path8766337Path.reset();
            path8766337Path.moveTo(563.78f, 1548.83f);
            path8766337Path.lineTo(482.95f, 1548.83f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766337Path, paint);
            canvas.restore();

            // path876-7-6-6-9
            RectF path8767669Rect = CacheForCanvas1.path8767669Rect;
            path8767669Rect.set(485.45f, 1553.16f, 566.28f, 1553.16f);
            Path path8767669Path = CacheForCanvas1.path8767669Path;
            path8767669Path.reset();
            path8767669Path.moveTo(566.28f, 1553.16f);
            path8767669Path.lineTo(485.45f, 1553.16f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767669Path, paint);
            canvas.restore();

            // path876-5-0-5-6
            RectF path8765056Rect = CacheForCanvas1.path8765056Rect;
            path8765056Rect.set(475.45f, 1535.84f, 556.28f, 1535.84f);
            Path path8765056Path = CacheForCanvas1.path8765056Path;
            path8765056Path.reset();
            path8765056Path.moveTo(556.28f, 1535.84f);
            path8765056Path.lineTo(475.45f, 1535.84f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765056Path, paint);
            canvas.restore();

            // path876-62-6-0
            RectF path8766260Rect = CacheForCanvas1.path8766260Rect;
            path8766260Rect.set(490.45f, 1561.82f, 571.28f, 1561.82f);
            Path path8766260Path = CacheForCanvas1.path8766260Path;
            path8766260Path.reset();
            path8766260Path.moveTo(571.28f, 1561.82f);
            path8766260Path.lineTo(490.45f, 1561.82f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766260Path, paint);
            canvas.restore();

            // path876-3-6-3-4
            RectF path8763634Rect = CacheForCanvas1.path8763634Rect;
            path8763634Rect.set(492.95f, 1566.15f, 573.78f, 1566.15f);
            Path path8763634Path = CacheForCanvas1.path8763634Path;
            path8763634Path.reset();
            path8763634Path.moveTo(573.78f, 1566.15f);
            path8763634Path.lineTo(492.95f, 1566.15f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763634Path, paint);
            canvas.restore();

            // path876-6-1-9-1
            RectF path8766191Rect = CacheForCanvas1.path8766191Rect;
            path8766191Rect.set(495.45f, 1570.48f, 576.28f, 1570.48f);
            Path path8766191Path = CacheForCanvas1.path8766191Path;
            path8766191Path.reset();
            path8766191Path.moveTo(576.28f, 1570.48f);
            path8766191Path.lineTo(495.45f, 1570.48f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766191Path, paint);
            canvas.restore();

            // path876-7-8-4-0
            RectF path8767840Rect = CacheForCanvas1.path8767840Rect;
            path8767840Rect.set(497.95f, 1574.81f, 578.78f, 1574.81f);
            Path path8767840Path = CacheForCanvas1.path8767840Path;
            path8767840Path.reset();
            path8767840Path.moveTo(578.78f, 1574.81f);
            path8767840Path.lineTo(497.95f, 1574.81f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767840Path, paint);
            canvas.restore();

            // path876-5-7-8-4
            RectF path8765784Rect = CacheForCanvas1.path8765784Rect;
            path8765784Rect.set(487.95f, 1557.49f, 568.78f, 1557.49f);
            Path path8765784Path = CacheForCanvas1.path8765784Path;
            path8765784Path.reset();
            path8765784Path.moveTo(568.78f, 1557.49f);
            path8765784Path.lineTo(487.95f, 1557.49f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765784Path, paint);
            canvas.restore();

            // path876-70-9-1-8
            RectF path87670918Rect = CacheForCanvas1.path87670918Rect;
            path87670918Rect.set(502.95f, 1583.47f, 583.78f, 1583.47f);
            Path path87670918Path = CacheForCanvas1.path87670918Path;
            path87670918Path.reset();
            path87670918Path.moveTo(583.78f, 1583.47f);
            path87670918Path.lineTo(502.95f, 1583.47f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87670918Path, paint);
            canvas.restore();

            // path876-3-9-2-2-7
            RectF path87639227Rect = CacheForCanvas1.path87639227Rect;
            path87639227Rect.set(505.45f, 1587.8f, 586.28f, 1587.8f);
            Path path87639227Path = CacheForCanvas1.path87639227Path;
            path87639227Path.reset();
            path87639227Path.moveTo(586.28f, 1587.8f);
            path87639227Path.lineTo(505.45f, 1587.8f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87639227Path, paint);
            canvas.restore();

            // path876-6-3-0-9-0
            RectF path87663090Rect = CacheForCanvas1.path87663090Rect;
            path87663090Rect.set(507.95f, 1592.13f, 588.78f, 1592.13f);
            Path path87663090Path = CacheForCanvas1.path87663090Path;
            path87663090Path.reset();
            path87663090Path.moveTo(588.78f, 1592.13f);
            path87663090Path.lineTo(507.95f, 1592.13f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87663090Path, paint);
            canvas.restore();

            // path876-7-6-2-3-86
            RectF path876762386Rect = CacheForCanvas1.path876762386Rect;
            path876762386Rect.set(510.45f, 1596.46f, 591.28f, 1596.46f);
            Path path876762386Path = CacheForCanvas1.path876762386Path;
            path876762386Path.reset();
            path876762386Path.moveTo(591.28f, 1596.46f);
            path876762386Path.lineTo(510.45f, 1596.46f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876762386Path, paint);
            canvas.restore();

            // path876-5-0-3-90-2
            RectF path876503902Rect = CacheForCanvas1.path876503902Rect;
            path876503902Rect.set(500.45f, 1579.14f, 581.28f, 1579.14f);
            Path path876503902Path = CacheForCanvas1.path876503902Path;
            path876503902Path.reset();
            path876503902Path.moveTo(581.28f, 1579.14f);
            path876503902Path.lineTo(500.45f, 1579.14f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876503902Path, paint);
            canvas.restore();

            // rect1446-47
            RectF rect144647Rect = CacheForCanvas1.rect144647Rect;
            rect144647Rect.set(390.27f, 1358.3f, 473f, 1401.6f);
            Path rect144647Path = CacheForCanvas1.rect144647Path;
            rect144647Path.reset();
            rect144647Path.moveTo(390.27f, 1358.3f);
            rect144647Path.lineTo(448f, 1358.3f);
            rect144647Path.lineTo(473f, 1401.6f);
            rect144647Path.lineTo(415.27f, 1401.6f);
            rect144647Path.lineTo(390.27f, 1358.3f);
            rect144647Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor4);
            canvas.drawPath(rect144647Path, paint);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.07f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(rect144647Path, paint);
            canvas.restore();

            // rect1521-8
            RectF rect15218Rect = CacheForCanvas1.rect15218Rect;
            rect15218Rect.set(232.78f, 1505.53f, 396.35f, 1548.83f);
            Path rect15218Path = CacheForCanvas1.rect15218Path;
            rect15218Path.reset();
            rect15218Path.moveTo(232.78f, 1505.53f);
            rect15218Path.lineTo(371.35f, 1505.53f);
            rect15218Path.lineTo(396.35f, 1548.83f);
            rect15218Path.lineTo(257.78f, 1548.83f);
            rect15218Path.lineTo(232.78f, 1505.53f);
            rect15218Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor2);
            canvas.drawPath(rect15218Path, paint);

            // path830-8-3
            RectF path83083Rect = CacheForCanvas1.path83083Rect;
            path83083Rect.set(216.94f, 1198.09f, 251.94f, 1258.71f);
            Path path83083Path = CacheForCanvas1.path83083Path;
            path83083Path.reset();
            path83083Path.moveTo(251.94f, 1258.71f);
            path83083Path.lineTo(216.94f, 1198.09f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path83083Path, paint);
            canvas.restore();

            // path876-8-0
            RectF path87680Rect = CacheForCanvas1.path87680Rect;
            path87680Rect.set(244.44f, 1245.72f, 302.17f, 1245.72f);
            Path path87680Path = CacheForCanvas1.path87680Path;
            path87680Path.reset();
            path87680Path.moveTo(302.17f, 1245.72f);
            path87680Path.lineTo(244.44f, 1245.72f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87680Path, paint);
            canvas.restore();

            // path876-3-5-1
            RectF path876351Rect = CacheForCanvas1.path876351Rect;
            path876351Rect.set(241.94f, 1241.39f, 299.67f, 1241.39f);
            Path path876351Path = CacheForCanvas1.path876351Path;
            path876351Path.reset();
            path876351Path.moveTo(299.67f, 1241.39f);
            path876351Path.lineTo(241.94f, 1241.39f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876351Path, paint);
            canvas.restore();

            // path876-6-0-7
            RectF path876607Rect = CacheForCanvas1.path876607Rect;
            path876607Rect.set(239.44f, 1237.06f, 297.17f, 1237.06f);
            Path path876607Path = CacheForCanvas1.path876607Path;
            path876607Path.reset();
            path876607Path.moveTo(297.17f, 1237.06f);
            path876607Path.lineTo(239.44f, 1237.06f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876607Path, paint);
            canvas.restore();

            // path876-7-96-8
            RectF path8767968Rect = CacheForCanvas1.path8767968Rect;
            path8767968Rect.set(236.94f, 1232.73f, 294.67f, 1232.73f);
            Path path8767968Path = CacheForCanvas1.path8767968Path;
            path8767968Path.reset();
            path8767968Path.moveTo(294.67f, 1232.73f);
            path8767968Path.lineTo(236.94f, 1232.73f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767968Path, paint);
            canvas.restore();

            // path876-5-3-9
            RectF path876539Rect = CacheForCanvas1.path876539Rect;
            path876539Rect.set(246.94f, 1250.05f, 304.67f, 1250.05f);
            Path path876539Path = CacheForCanvas1.path876539Path;
            path876539Path.reset();
            path876539Path.moveTo(304.67f, 1250.05f);
            path876539Path.lineTo(246.94f, 1250.05f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876539Path, paint);
            canvas.restore();

            // path876-70-8-1
            RectF path8767081Rect = CacheForCanvas1.path8767081Rect;
            path8767081Rect.set(231.94f, 1224.07f, 289.67f, 1224.07f);
            Path path8767081Path = CacheForCanvas1.path8767081Path;
            path8767081Path.reset();
            path8767081Path.moveTo(289.67f, 1224.07f);
            path8767081Path.lineTo(231.94f, 1224.07f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767081Path, paint);
            canvas.restore();

            // path876-3-9-5-5
            RectF path8763955Rect = CacheForCanvas1.path8763955Rect;
            path8763955Rect.set(229.44f, 1219.74f, 287.17f, 1219.74f);
            Path path8763955Path = CacheForCanvas1.path8763955Path;
            path8763955Path.reset();
            path8763955Path.moveTo(287.17f, 1219.74f);
            path8763955Path.lineTo(229.44f, 1219.74f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763955Path, paint);
            canvas.restore();

            // path876-6-3-6-4
            RectF path8766364Rect = CacheForCanvas1.path8766364Rect;
            path8766364Rect.set(226.94f, 1215.41f, 284.67f, 1215.41f);
            Path path8766364Path = CacheForCanvas1.path8766364Path;
            path8766364Path.reset();
            path8766364Path.moveTo(284.67f, 1215.41f);
            path8766364Path.lineTo(226.94f, 1215.41f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766364Path, paint);
            canvas.restore();

            // path876-7-6-1-9
            RectF path8767619Rect = CacheForCanvas1.path8767619Rect;
            path8767619Rect.set(224.44f, 1211.08f, 282.17f, 1211.08f);
            Path path8767619Path = CacheForCanvas1.path8767619Path;
            path8767619Path.reset();
            path8767619Path.moveTo(282.17f, 1211.08f);
            path8767619Path.lineTo(224.44f, 1211.08f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767619Path, paint);
            canvas.restore();

            // path876-5-0-1-2
            RectF path8765012Rect = CacheForCanvas1.path8765012Rect;
            path8765012Rect.set(234.44f, 1228.4f, 292.17f, 1228.4f);
            Path path8765012Path = CacheForCanvas1.path8765012Path;
            path8765012Path.reset();
            path8765012Path.moveTo(292.17f, 1228.4f);
            path8765012Path.lineTo(234.44f, 1228.4f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765012Path, paint);
            canvas.restore();

            // path876-62-5-5
            RectF path8766255Rect = CacheForCanvas1.path8766255Rect;
            path8766255Rect.set(219.44f, 1202.42f, 277.17f, 1202.42f);
            Path path8766255Path = CacheForCanvas1.path8766255Path;
            path8766255Path.reset();
            path8766255Path.moveTo(277.17f, 1202.42f);
            path8766255Path.lineTo(219.44f, 1202.42f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766255Path, paint);
            canvas.restore();

            // path876-3-6-9-7
            RectF path8763697Rect = CacheForCanvas1.path8763697Rect;
            path8763697Rect.set(216.94f, 1198.09f, 274.67f, 1198.09f);
            Path path8763697Path = CacheForCanvas1.path8763697Path;
            path8763697Path.reset();
            path8763697Path.moveTo(274.67f, 1198.09f);
            path8763697Path.lineTo(216.94f, 1198.09f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763697Path, paint);
            canvas.restore();

            // path876-6-1-8-4
            RectF path8766184Rect = CacheForCanvas1.path8766184Rect;
            path8766184Rect.set(214.44f, 1193.76f, 272.17f, 1193.76f);
            Path path8766184Path = CacheForCanvas1.path8766184Path;
            path8766184Path.reset();
            path8766184Path.moveTo(272.17f, 1193.76f);
            path8766184Path.lineTo(214.44f, 1193.76f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766184Path, paint);
            canvas.restore();

            // path876-7-8-48-9
            RectF path87678489Rect = CacheForCanvas1.path87678489Rect;
            path87678489Rect.set(211.94f, 1189.43f, 269.67f, 1189.43f);
            Path path87678489Path = CacheForCanvas1.path87678489Path;
            path87678489Path.reset();
            path87678489Path.moveTo(269.67f, 1189.43f);
            path87678489Path.lineTo(211.94f, 1189.43f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87678489Path, paint);
            canvas.restore();

            // path876-5-7-1-9
            RectF path8765719Rect = CacheForCanvas1.path8765719Rect;
            path8765719Rect.set(221.94f, 1206.75f, 279.67f, 1206.75f);
            Path path8765719Path = CacheForCanvas1.path8765719Path;
            path8765719Path.reset();
            path8765719Path.moveTo(279.67f, 1206.75f);
            path8765719Path.lineTo(221.94f, 1206.75f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765719Path, paint);
            canvas.restore();

            // path876-70-9-0-4
            RectF path87670904Rect = CacheForCanvas1.path87670904Rect;
            path87670904Rect.set(206.94f, 1180.77f, 264.67f, 1180.77f);
            Path path87670904Path = CacheForCanvas1.path87670904Path;
            path87670904Path.reset();
            path87670904Path.moveTo(264.67f, 1180.77f);
            path87670904Path.lineTo(206.94f, 1180.77f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87670904Path, paint);
            canvas.restore();

            // path876-3-9-2-3-5
            RectF path87639235Rect = CacheForCanvas1.path87639235Rect;
            path87639235Rect.set(204.44f, 1176.44f, 262.17f, 1176.44f);
            Path path87639235Path = CacheForCanvas1.path87639235Path;
            path87639235Path.reset();
            path87639235Path.moveTo(262.17f, 1176.44f);
            path87639235Path.lineTo(204.44f, 1176.44f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87639235Path, paint);
            canvas.restore();

            // path876-6-3-0-0-9
            RectF path87663009Rect = CacheForCanvas1.path87663009Rect;
            path87663009Rect.set(201.94f, 1172.11f, 259.67f, 1172.11f);
            Path path87663009Path = CacheForCanvas1.path87663009Path;
            path87663009Path.reset();
            path87663009Path.moveTo(259.67f, 1172.11f);
            path87663009Path.lineTo(201.94f, 1172.11f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87663009Path, paint);
            canvas.restore();

            // path876-7-6-2-44-3
            RectF path876762443Rect = CacheForCanvas1.path876762443Rect;
            path876762443Rect.set(199.44f, 1167.78f, 257.17f, 1167.78f);
            Path path876762443Path = CacheForCanvas1.path876762443Path;
            path876762443Path.reset();
            path876762443Path.moveTo(257.17f, 1167.78f);
            path876762443Path.lineTo(199.44f, 1167.78f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876762443Path, paint);
            canvas.restore();

            // path876-5-0-3-4-5
            RectF path87650345Rect = CacheForCanvas1.path87650345Rect;
            path87650345Rect.set(209.44f, 1185.1f, 267.17f, 1185.1f);
            Path path87650345Path = CacheForCanvas1.path87650345Path;
            path87650345Path.reset();
            path87650345Path.moveTo(267.17f, 1185.1f);
            path87650345Path.lineTo(209.44f, 1185.1f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87650345Path, paint);
            canvas.restore();

            // rect1196-4-7
            RectF rect119647Rect = CacheForCanvas1.rect119647Rect;
            rect119647Rect.set(257.17f, 1167.78f, 310.45f, 1250.05f);
            Path rect119647Path = CacheForCanvas1.rect119647Path;
            rect119647Path.reset();
            rect119647Path.moveTo(304.67f, 1250.05f);
            rect119647Path.lineTo(310.45f, 1250.05f);
            rect119647Path.lineTo(262.95f, 1167.78f);
            rect119647Path.lineTo(257.17f, 1167.78f);
            rect119647Path.lineTo(304.67f, 1250.05f);
            rect119647Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor4);
            canvas.drawPath(rect119647Path, paint);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.07f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(rect119647Path, paint);
            canvas.restore();

            // path876-30-7-0
            RectF path8763070Rect = CacheForCanvas1.path8763070Rect;
            path8763070Rect.set(394.55f, 1245.72f, 452.29f, 1245.72f);
            Path path8763070Path = CacheForCanvas1.path8763070Path;
            path8763070Path.reset();
            path8763070Path.moveTo(394.55f, 1245.72f);
            path8763070Path.lineTo(452.29f, 1245.72f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763070Path, paint);
            canvas.restore();

            // path876-3-7-6-8
            RectF path8763768Rect = CacheForCanvas1.path8763768Rect;
            path8763768Rect.set(392.05f, 1241.39f, 449.79f, 1241.39f);
            Path path8763768Path = CacheForCanvas1.path8763768Path;
            path8763768Path.reset();
            path8763768Path.moveTo(392.05f, 1241.39f);
            path8763768Path.lineTo(449.79f, 1241.39f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763768Path, paint);
            canvas.restore();

            // path876-6-8-3-1
            RectF path8766831Rect = CacheForCanvas1.path8766831Rect;
            path8766831Rect.set(389.55f, 1237.06f, 447.29f, 1237.06f);
            Path path8766831Path = CacheForCanvas1.path8766831Path;
            path8766831Path.reset();
            path8766831Path.moveTo(389.55f, 1237.06f);
            path8766831Path.lineTo(447.29f, 1237.06f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766831Path, paint);
            canvas.restore();

            // path876-7-68-1-9
            RectF path87676819Rect = CacheForCanvas1.path87676819Rect;
            path87676819Rect.set(387.05f, 1232.73f, 444.79f, 1232.73f);
            Path path87676819Path = CacheForCanvas1.path87676819Path;
            path87676819Path.reset();
            path87676819Path.moveTo(387.05f, 1232.73f);
            path87676819Path.lineTo(444.79f, 1232.73f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87676819Path, paint);
            canvas.restore();

            // path876-5-8-7-9
            RectF path8765879Rect = CacheForCanvas1.path8765879Rect;
            path8765879Rect.set(397.05f, 1250.05f, 454.79f, 1250.05f);
            Path path8765879Path = CacheForCanvas1.path8765879Path;
            path8765879Path.reset();
            path8765879Path.moveTo(397.05f, 1250.05f);
            path8765879Path.lineTo(454.79f, 1250.05f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765879Path, paint);
            canvas.restore();

            // path876-70-4-5-7
            RectF path87670457Rect = CacheForCanvas1.path87670457Rect;
            path87670457Rect.set(382.05f, 1224.07f, 439.79f, 1224.07f);
            Path path87670457Path = CacheForCanvas1.path87670457Path;
            path87670457Path.reset();
            path87670457Path.moveTo(382.05f, 1224.07f);
            path87670457Path.lineTo(439.79f, 1224.07f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87670457Path, paint);
            canvas.restore();

            // path876-3-9-3-9-8
            RectF path87639398Rect = CacheForCanvas1.path87639398Rect;
            path87639398Rect.set(379.55f, 1219.74f, 437.29f, 1219.74f);
            Path path87639398Path = CacheForCanvas1.path87639398Path;
            path87639398Path.reset();
            path87639398Path.moveTo(379.55f, 1219.74f);
            path87639398Path.lineTo(437.29f, 1219.74f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87639398Path, paint);
            canvas.restore();

            // path876-6-3-1-6- 3
            RectF path87663163Rect = CacheForCanvas1.path87663163Rect;
            path87663163Rect.set(377.05f, 1215.41f, 434.79f, 1215.41f);
            Path path87663163Path = CacheForCanvas1.path87663163Path;
            path87663163Path.reset();
            path87663163Path.moveTo(377.05f, 1215.41f);
            path87663163Path.lineTo(434.79f, 1215.41f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87663163Path, paint);
            canvas.restore();

            // path876-7-6-4-2-5
            RectF path87676425Rect = CacheForCanvas1.path87676425Rect;
            path87676425Rect.set(374.55f, 1211.08f, 432.29f, 1211.08f);
            Path path87676425Path = CacheForCanvas1.path87676425Path;
            path87676425Path.reset();
            path87676425Path.moveTo(374.55f, 1211.08f);
            path87676425Path.lineTo(432.29f, 1211.08f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87676425Path, paint);
            canvas.restore();

            // path876-5-0-9-1-3
            RectF path87650913Rect = CacheForCanvas1.path87650913Rect;
            path87650913Rect.set(384.55f, 1228.4f, 442.29f, 1228.4f);
            Path path87650913Path = CacheForCanvas1.path87650913Path;
            path87650913Path.reset();
            path87650913Path.moveTo(384.55f, 1228.4f);
            path87650913Path.lineTo(442.29f, 1228.4f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87650913Path, paint);
            canvas.restore();

            // path876-62-2-7-4
            RectF path87662274Rect = CacheForCanvas1.path87662274Rect;
            path87662274Rect.set(369.55f, 1202.42f, 427.29f, 1202.42f);
            Path path87662274Path = CacheForCanvas1.path87662274Path;
            path87662274Path.reset();
            path87662274Path.moveTo(369.55f, 1202.42f);
            path87662274Path.lineTo(427.29f, 1202.42f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87662274Path, paint);
            canvas.restore();

            // path876-3-6-0-8-9
            RectF path87636089Rect = CacheForCanvas1.path87636089Rect;
            path87636089Rect.set(367.05f, 1198.09f, 424.79f, 1198.09f);
            Path path87636089Path = CacheForCanvas1.path87636089Path;
            path87636089Path.reset();
            path87636089Path.moveTo(367.05f, 1198.09f);
            path87636089Path.lineTo(424.79f, 1198.09f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87636089Path, paint);
            canvas.restore();

            // path876-6-1-6-5-0
            RectF path87661650Rect = CacheForCanvas1.path87661650Rect;
            path87661650Rect.set(364.55f, 1193.76f, 422.29f, 1193.76f);
            Path path87661650Path = CacheForCanvas1.path87661650Path;
            path87661650Path.reset();
            path87661650Path.moveTo(364.55f, 1193.76f);
            path87661650Path.lineTo(422.29f, 1193.76f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87661650Path, paint);
            canvas.restore();

            // path876-7-8-8-7-2
            RectF path87678872Rect = CacheForCanvas1.path87678872Rect;
            path87678872Rect.set(362.05f, 1189.43f, 419.79f, 1189.43f);
            Path path87678872Path = CacheForCanvas1.path87678872Path;
            path87678872Path.reset();
            path87678872Path.moveTo(362.05f, 1189.43f);
            path87678872Path.lineTo(419.79f, 1189.43f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87678872Path, paint);
            canvas.restore();

            // path876-5-7-9-4-0
            RectF path87657940Rect = CacheForCanvas1.path87657940Rect;
            path87657940Rect.set(372.05f, 1206.75f, 429.79f, 1206.75f);
            Path path87657940Path = CacheForCanvas1.path87657940Path;
            path87657940Path.reset();
            path87657940Path.moveTo(372.05f, 1206.75f);
            path87657940Path.lineTo(429.79f, 1206.75f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87657940Path, paint);
            canvas.restore();

            // path876-70-9-2-1-1
            RectF path876709211Rect = CacheForCanvas1.path876709211Rect;
            path876709211Rect.set(357.05f, 1180.77f, 414.79f, 1180.77f);
            Path path876709211Path = CacheForCanvas1.path876709211Path;
            path876709211Path.reset();
            path876709211Path.moveTo(357.05f, 1180.77f);
            path876709211Path.lineTo(414.79f, 1180.77f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876709211Path, paint);
            canvas.restore();

            // path876-3-9-2-6-8-9
            RectF path876392689Rect = CacheForCanvas1.path876392689Rect;
            path876392689Rect.set(354.55f, 1176.44f, 412.29f, 1176.44f);
            Path path876392689Path = CacheForCanvas1.path876392689Path;
            path876392689Path.reset();
            path876392689Path.moveTo(354.55f, 1176.44f);
            path876392689Path.lineTo(412.29f, 1176.44f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876392689Path, paint);
            canvas.restore();

            // path876-6-3-0-6-5-6
            RectF path876630656Rect = CacheForCanvas1.path876630656Rect;
            path876630656Rect.set(352.05f, 1172.11f, 409.79f, 1172.11f);
            Path path876630656Path = CacheForCanvas1.path876630656Path;
            path876630656Path.reset();
            path876630656Path.moveTo(352.05f, 1172.11f);
            path876630656Path.lineTo(409.79f, 1172.11f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876630656Path, paint);
            canvas.restore();

            // path876-7-6-2-4-9-2
            RectF path876762492Rect = CacheForCanvas1.path876762492Rect;
            path876762492Rect.set(349.55f, 1167.78f, 407.29f, 1167.78f);
            Path path876762492Path = CacheForCanvas1.path876762492Path;
            path876762492Path.reset();
            path876762492Path.moveTo(349.55f, 1167.78f);
            path876762492Path.lineTo(407.29f, 1167.78f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876762492Path, paint);
            canvas.restore();

            // path876-5-0-3-9-7-1
            RectF path876503971Rect = CacheForCanvas1.path876503971Rect;
            path876503971Rect.set(359.55f, 1185.1f, 417.29f, 1185.1f);
            Path path876503971Path = CacheForCanvas1.path876503971Path;
            path876503971Path.reset();
            path876503971Path.moveTo(359.55f, 1185.1f);
            path876503971Path.lineTo(417.29f, 1185.1f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876503971Path, paint);
            canvas.restore();

            // rect1196-5-5-2
            RectF rect1196552Rect = CacheForCanvas1.rect1196552Rect;
            rect1196552Rect.set(343.78f, 1167.78f, 397.05f, 1250.05f);
            Path rect1196552Path = CacheForCanvas1.rect1196552Path;
            rect1196552Path.reset();
            rect1196552Path.moveTo(397.05f, 1250.05f);
            rect1196552Path.lineTo(391.28f, 1250.05f);
            rect1196552Path.lineTo(343.78f, 1167.78f);
            rect1196552Path.lineTo(349.55f, 1167.78f);
            rect1196552Path.lineTo(397.05f, 1250.05f);
            rect1196552Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor4);
            canvas.drawPath(rect1196552Path, paint);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.07f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(rect1196552Path, paint);
            canvas.restore();

            // path876-1-3-0
            RectF path876130Rect = CacheForCanvas1.path876130Rect;
            path876130Rect.set(307.95f, 1245.72f, 388.78f, 1245.72f);
            Path path876130Path = CacheForCanvas1.path876130Path;
            path876130Path.reset();
            path876130Path.moveTo(388.78f, 1245.72f);
            path876130Path.lineTo(307.95f, 1245.72f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876130Path, paint);
            canvas.restore();

            // path876-3-1-8-7
            RectF path8763187Rect = CacheForCanvas1.path8763187Rect;
            path8763187Rect.set(305.45f, 1241.39f, 386.28f, 1241.39f);
            Path path8763187Path = CacheForCanvas1.path8763187Path;
            path8763187Path.reset();
            path8763187Path.moveTo(386.28f, 1241.39f);
            path8763187Path.lineTo(305.45f, 1241.39f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763187Path, paint);
            canvas.restore();

            // path876-6-5-8-3
            RectF path8766583Rect = CacheForCanvas1.path8766583Rect;
            path8766583Rect.set(302.95f, 1237.06f, 383.78f, 1237.06f);
            Path path8766583Path = CacheForCanvas1.path8766583Path;
            path8766583Path.reset();
            path8766583Path.moveTo(383.78f, 1237.06f);
            path8766583Path.lineTo(302.95f, 1237.06f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766583Path, paint);
            canvas.restore();

            // path876-7-9-3- 2
            RectF path8767932Rect = CacheForCanvas1.path8767932Rect;
            path8767932Rect.set(300.45f, 1232.73f, 381.28f, 1232.73f);
            Path path8767932Path = CacheForCanvas1.path8767932Path;
            path8767932Path.reset();
            path8767932Path.moveTo(381.28f, 1232.73f);
            path8767932Path.lineTo(300.45f, 1232.73f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767932Path, paint);
            canvas.restore();

            // path876-5-77-1-1
            RectF path87657711Rect = CacheForCanvas1.path87657711Rect;
            path87657711Rect.set(310.45f, 1250.05f, 391.28f, 1250.05f);
            Path path87657711Path = CacheForCanvas1.path87657711Path;
            path87657711Path.reset();
            path87657711Path.moveTo(391.28f, 1250.05f);
            path87657711Path.lineTo(310.45f, 1250.05f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87657711Path, paint);
            canvas.restore();

            // path876-70-6-8-9
            RectF path87670689Rect = CacheForCanvas1.path87670689Rect;
            path87670689Rect.set(295.45f, 1224.07f, 376.28f, 1224.07f);
            Path path87670689Path = CacheForCanvas1.path87670689Path;
            path87670689Path.reset();
            path87670689Path.moveTo(376.28f, 1224.07f);
            path87670689Path.lineTo(295.45f, 1224.07f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87670689Path, paint);
            canvas.restore();

            // path876-3-9-7-9-0
            RectF path87639790Rect = CacheForCanvas1.path87639790Rect;
            path87639790Rect.set(292.95f, 1219.74f, 373.78f, 1219.74f);
            Path path87639790Path = CacheForCanvas1.path87639790Path;
            path87639790Path.reset();
            path87639790Path.moveTo(373.78f, 1219.74f);
            path87639790Path.lineTo(292.95f, 1219.74f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87639790Path, paint);
            canvas.restore();

            // path876-6-3-3-6-5
            RectF path87663365Rect = CacheForCanvas1.path87663365Rect;
            path87663365Rect.set(290.45f, 1215.41f, 371.28f, 1215.41f);
            Path path87663365Path = CacheForCanvas1.path87663365Path;
            path87663365Path.reset();
            path87663365Path.moveTo(371.28f, 1215.41f);
            path87663365Path.lineTo(290.45f, 1215.41f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87663365Path, paint);
            canvas.restore();

            // path876-7-6-6-4-6
            RectF path87676646Rect = CacheForCanvas1.path87676646Rect;
            path87676646Rect.set(287.95f, 1211.08f, 368.78f, 1211.08f);
            Path path87676646Path = CacheForCanvas1.path87676646Path;
            path87676646Path.reset();
            path87676646Path.moveTo(368.78f, 1211.08f);
            path87676646Path.lineTo(287.95f, 1211.08f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87676646Path, paint);
            canvas.restore();

            // path876-5-0-5-3-7
            RectF path87650537Rect = CacheForCanvas1.path87650537Rect;
            path87650537Rect.set(297.95f, 1228.4f, 378.78f, 1228.4f);
            Path path87650537Path = CacheForCanvas1.path87650537Path;
            path87650537Path.reset();
            path87650537Path.moveTo(378.78f, 1228.4f);
            path87650537Path.lineTo(297.95f, 1228.4f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87650537Path, paint);
            canvas.restore();

            // path876-62-6-3-7
            RectF path87662637Rect = CacheForCanvas1.path87662637Rect;
            path87662637Rect.set(282.95f, 1202.42f, 363.78f, 1202.42f);
            Path path87662637Path = CacheForCanvas1.path87662637Path;
            path87662637Path.reset();
            path87662637Path.moveTo(363.78f, 1202.42f);
            path87662637Path.lineTo(282.95f, 1202.42f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87662637Path, paint);
            canvas.restore();

            // path876-3-6-3-3-4
            RectF path87636334Rect = CacheForCanvas1.path87636334Rect;
            path87636334Rect.set(280.45f, 1198.09f, 361.28f, 1198.09f);
            Path path87636334Path = CacheForCanvas1.path87636334Path;
            path87636334Path.reset();
            path87636334Path.moveTo(361.28f, 1198.09f);
            path87636334Path.lineTo(280.45f, 1198.09f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87636334Path, paint);
            canvas.restore();

            // path876-6-1-9-8-0
            RectF path87661980Rect = CacheForCanvas1.path87661980Rect;
            path87661980Rect.set(277.95f, 1193.76f, 358.78f, 1193.76f);
            Path path87661980Path = CacheForCanvas1.path87661980Path;
            path87661980Path.reset();
            path87661980Path.moveTo(358.78f, 1193.76f);
            path87661980Path.lineTo(277.95f, 1193.76f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87661980Path, paint);
            canvas.restore();

            // path876-7-8-4-6-6
            RectF path87678466Rect = CacheForCanvas1.path87678466Rect;
            path87678466Rect.set(275.45f, 1189.43f, 356.28f, 1189.43f);
            Path path87678466Path = CacheForCanvas1.path87678466Path;
            path87678466Path.reset();
            path87678466Path.moveTo(356.28f, 1189.43f);
            path87678466Path.lineTo(275.45f, 1189.43f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87678466Path, paint);
            canvas.restore();

            // path876-5-7-8-0-4
            RectF path87657804Rect = CacheForCanvas1.path87657804Rect;
            path87657804Rect.set(285.45f, 1206.75f, 366.28f, 1206.75f);
            Path path87657804Path = CacheForCanvas1.path87657804Path;
            path87657804Path.reset();
            path87657804Path.moveTo(366.28f, 1206.75f);
            path87657804Path.lineTo(285.45f, 1206.75f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87657804Path, paint);
            canvas.restore();

            // path876-70-9-1-4-7
            RectF path876709147Rect = CacheForCanvas1.path876709147Rect;
            path876709147Rect.set(270.45f, 1180.77f, 351.28f, 1180.77f);
            Path path876709147Path = CacheForCanvas1.path876709147Path;
            path876709147Path.reset();
            path876709147Path.moveTo(351.28f, 1180.77f);
            path876709147Path.lineTo(270.45f, 1180.77f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876709147Path, paint);
            canvas.restore();

            // path876-3-9-2-2-8-4
            RectF path876392284Rect = CacheForCanvas1.path876392284Rect;
            path876392284Rect.set(267.95f, 1176.44f, 348.78f, 1176.44f);
            Path path876392284Path = CacheForCanvas1.path876392284Path;
            path876392284Path.reset();
            path876392284Path.moveTo(348.78f, 1176.44f);
            path876392284Path.lineTo(267.95f, 1176.44f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876392284Path, paint);
            canvas.restore();

            // path876-6-3-0-9-8-8
            RectF path876630988Rect = CacheForCanvas1.path876630988Rect;
            path876630988Rect.set(265.45f, 1172.11f, 346.28f, 1172.11f);
            Path path876630988Path = CacheForCanvas1.path876630988Path;
            path876630988Path.reset();
            path876630988Path.moveTo(346.28f, 1172.11f);
            path876630988Path.lineTo(265.45f, 1172.11f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876630988Path, paint);
            canvas.restore();

            // path876-7-6-2-3-8-5
            RectF path876762385Rect = CacheForCanvas1.path876762385Rect;
            path876762385Rect.set(262.95f, 1167.78f, 343.78f, 1167.78f);
            Path path876762385Path = CacheForCanvas1.path876762385Path;
            path876762385Path.reset();
            path876762385Path.moveTo(343.78f, 1167.78f);
            path876762385Path.lineTo(262.95f, 1167.78f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876762385Path, paint);
            canvas.restore();

            // path876-5-0-3-90-9-8
            RectF path8765039098Rect = CacheForCanvas1.path8765039098Rect;
            path8765039098Rect.set(272.95f, 1185.1f, 353.78f, 1185.1f);
            Path path8765039098Path = CacheForCanvas1.path8765039098Path;
            path8765039098Path.reset();
            path8765039098Path.moveTo(353.78f, 1185.1f);
            path8765039098Path.lineTo(272.95f, 1185.1f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765039098Path, paint);
            canvas.restore();

            // rect1521-8-2
            RectF rect152182Rect = CacheForCanvas1.rect152182Rect;
            rect152182Rect.set(358.36f, 1263.04f, 472.64f, 1340.98f);
            Path rect152182Path = CacheForCanvas1.rect152182Path;
            rect152182Path.reset();
            rect152182Path.moveTo(358.36f, 1263.04f);
            rect152182Path.lineTo(427.64f, 1263.04f);
            rect152182Path.lineTo(472.64f, 1340.98f);
            rect152182Path.lineTo(403.36f, 1340.98f);
            rect152182Path.lineTo(358.36f, 1263.04f);
            rect152182Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor2);
            canvas.drawPath(rect152182Path, paint);

            // path828-60
            RectF path82860Rect = CacheForCanvas1.path82860Rect;
            path82860Rect.set(174.44f, 1684.48f, 873.58f, 2195.43f);
            Path path82860Path = CacheForCanvas1.path82860Path;
            path82860Path.reset();
            path82860Path.moveTo(688.58f, 1875f);
            path82860Path.lineTo(763.58f, 2004.91f);
            path82860Path.moveTo(783.53f, 2039.46f);
            path82860Path.lineTo(873.58f, 2195.43f);
            path82860Path.lineTo(683.15f, 2195.43f);
            path82860Path.lineTo(469.44f, 2195.43f);
            path82860Path.lineTo(429.44f, 2126.15f);
            path82860Path.moveTo(214.44f, 1753.76f);
            path82860Path.lineTo(174.44f, 1684.48f);
            path82860Path.lineTo(578.58f, 1684.48f);
            path82860Path.lineTo(668.58f, 1840.36f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path82860Path, paint);
            canvas.restore();

            // path830-6
            RectF path8306Rect = CacheForCanvas1.path8306Rect;
            path8306Rect.set(394.44f, 2065.53f, 429.44f, 2126.15f);
            Path path8306Path = CacheForCanvas1.path8306Path;
            path8306Path.reset();
            path8306Path.moveTo(394.44f, 2065.53f);
            path8306Path.lineTo(429.44f, 2126.15f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8306Path, paint);
            canvas.restore();

            // path836-6
            RectF path8366Rect = CacheForCanvas1.path8366Rect;
            path8366Rect.set(309.44f, 1918.3f, 334.44f, 1961.6f);
            Path path8366Path = CacheForCanvas1.path8366Path;
            path8366Path.reset();
            path8366Path.moveTo(334.44f, 1961.6f);
            path8366Path.lineTo(309.44f, 1918.3f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8366Path, paint);
            canvas.restore();

            // path842-9
            RectF path8429Rect = CacheForCanvas1.path8429Rect;
            path8429Rect.set(214.44f, 1753.76f, 249.44f, 1814.38f);
            Path path8429Path = CacheForCanvas1.path8429Path;
            path8429Path.reset();
            path8429Path.moveTo(249.44f, 1814.38f);
            path8429Path.lineTo(214.44f, 1753.76f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8429Path, paint);
            canvas.restore();

            // path844-60
            RectF path84460Rect = CacheForCanvas1.path84460Rect;
            path84460Rect.set(404.79f, 1723.45f, 454.79f, 1810.05f);
            Path path84460Path = CacheForCanvas1.path84460Path;
            path84460Path.reset();
            path84460Path.moveTo(454.79f, 1810.05f);
            path84460Path.lineTo(404.79f, 1723.45f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path84460Path, paint);
            canvas.restore();

            // path846-7
            RectF path8467Rect = CacheForCanvas1.path8467Rect;
            path8467Rect.set(607.29f, 2074.19f, 657.29f, 2160.79f);
            Path path8467Path = CacheForCanvas1.path8467Path;
            path8467Path.reset();
            path8467Path.moveTo(607.29f, 2074.19f);
            path8467Path.lineTo(657.29f, 2160.79f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8467Path, paint);
            canvas.restore();

            // path876-0
            RectF path8760Rect = CacheForCanvas1.path8760Rect;
            path8760Rect.set(401.94f, 2078.52f, 459.67f, 2078.52f);
            Path path8760Path = CacheForCanvas1.path8760Path;
            path8760Path.reset();
            path8760Path.moveTo(459.67f, 2078.52f);
            path8760Path.lineTo(401.94f, 2078.52f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8760Path, paint);
            canvas.restore();

            // path876-3- 4
            RectF path87634Rect = CacheForCanvas1.path87634Rect;
            path87634Rect.set(404.44f, 2082.85f, 462.17f, 2082.85f);
            Path path87634Path = CacheForCanvas1.path87634Path;
            path87634Path.reset();
            path87634Path.moveTo(462.17f, 2082.85f);
            path87634Path.lineTo(404.44f, 2082.85f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87634Path, paint);
            canvas.restore();

            // path876-6-13
            RectF path876613Rect = CacheForCanvas1.path876613Rect;
            path876613Rect.set(406.94f, 2087.18f, 464.67f, 2087.18f);
            Path path876613Path = CacheForCanvas1.path876613Path;
            path876613Path.reset();
            path876613Path.moveTo(464.67f, 2087.18f);
            path876613Path.lineTo(406.94f, 2087.18f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876613Path, paint);
            canvas.restore();

            // path876-7-7
            RectF path87677Rect = CacheForCanvas1.path87677Rect;
            path87677Rect.set(409.44f, 2091.51f, 467.17f, 2091.51f);
            Path path87677Path = CacheForCanvas1.path87677Path;
            path87677Path.reset();
            path87677Path.moveTo(467.17f, 2091.51f);
            path87677Path.lineTo(409.44f, 2091.51f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87677Path, paint);
            canvas.restore();

            // path876-5- 2
            RectF path87652Rect = CacheForCanvas1.path87652Rect;
            path87652Rect.set(399.44f, 2074.19f, 457.17f, 2074.19f);
            Path path87652Path = CacheForCanvas1.path87652Path;
            path87652Path.reset();
            path87652Path.moveTo(457.17f, 2074.19f);
            path87652Path.lineTo(399.44f, 2074.19f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87652Path, paint);
            canvas.restore();

            // path876-70-64
            RectF path8767064Rect = CacheForCanvas1.path8767064Rect;
            path8767064Rect.set(414.44f, 2100.17f, 472.17f, 2100.17f);
            Path path8767064Path = CacheForCanvas1.path8767064Path;
            path8767064Path.reset();
            path8767064Path.moveTo(472.17f, 2100.17f);
            path8767064Path.lineTo(414.44f, 2100.17f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767064Path, paint);
            canvas.restore();

            // path876-3-9-52
            RectF path8763952Rect = CacheForCanvas1.path8763952Rect;
            path8763952Rect.set(416.94f, 2104.5f, 474.67f, 2104.5f);
            Path path8763952Path = CacheForCanvas1.path8763952Path;
            path8763952Path.reset();
            path8763952Path.moveTo(474.67f, 2104.5f);
            path8763952Path.lineTo(416.94f, 2104.5f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763952Path, paint);
            canvas.restore();

            // path876-6-3- 2
            RectF path876632Rect = CacheForCanvas1.path876632Rect;
            path876632Rect.set(419.44f, 2108.83f, 477.17f, 2108.83f);
            Path path876632Path = CacheForCanvas1.path876632Path;
            path876632Path.reset();
            path876632Path.moveTo(477.17f, 2108.83f);
            path876632Path.lineTo(419.44f, 2108.83f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876632Path, paint);
            canvas.restore();

            // path876-7-6-9
            RectF path876769Rect = CacheForCanvas1.path876769Rect;
            path876769Rect.set(421.94f, 2113.16f, 479.67f, 2113.16f);
            Path path876769Path = CacheForCanvas1.path876769Path;
            path876769Path.reset();
            path876769Path.moveTo(479.67f, 2113.16f);
            path876769Path.lineTo(421.94f, 2113.16f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876769Path, paint);
            canvas.restore();

            // path876-5-0-0
            RectF path876500Rect = CacheForCanvas1.path876500Rect;
            path876500Rect.set(411.94f, 2095.84f, 469.67f, 2095.84f);
            Path path876500Path = CacheForCanvas1.path876500Path;
            path876500Path.reset();
            path876500Path.moveTo(469.67f, 2095.84f);
            path876500Path.lineTo(411.94f, 2095.84f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876500Path, paint);
            canvas.restore();

            // path876-62-9
            RectF path876629Rect = CacheForCanvas1.path876629Rect;
            path876629Rect.set(426.94f, 2121.82f, 484.67f, 2121.82f);
            Path path876629Path = CacheForCanvas1.path876629Path;
            path876629Path.reset();
            path876629Path.moveTo(484.67f, 2121.82f);
            path876629Path.lineTo(426.94f, 2121.82f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876629Path, paint);
            canvas.restore();

            // path876-3-6-94
            RectF path8763694Rect = CacheForCanvas1.path8763694Rect;
            path8763694Rect.set(429.44f, 2126.15f, 487.17f, 2126.15f);
            Path path8763694Path = CacheForCanvas1.path8763694Path;
            path8763694Path.reset();
            path8763694Path.moveTo(487.17f, 2126.15f);
            path8763694Path.lineTo(429.44f, 2126.15f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763694Path, paint);
            canvas.restore();

            // path876-6-1-5
            RectF path876615Rect = CacheForCanvas1.path876615Rect;
            path876615Rect.set(431.94f, 2130.48f, 489.67f, 2130.48f);
            Path path876615Path = CacheForCanvas1.path876615Path;
            path876615Path.reset();
            path876615Path.moveTo(489.67f, 2130.48f);
            path876615Path.lineTo(431.94f, 2130.48f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876615Path, paint);
            canvas.restore();

            // path876-7-8-10
            RectF path8767810Rect = CacheForCanvas1.path8767810Rect;
            path8767810Rect.set(434.44f, 2134.81f, 492.17f, 2134.81f);
            Path path8767810Path = CacheForCanvas1.path8767810Path;
            path8767810Path.reset();
            path8767810Path.moveTo(492.17f, 2134.81f);
            path8767810Path.lineTo(434.44f, 2134.81f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767810Path, paint);
            canvas.restore();

            // path876-5-7-37
            RectF path8765737Rect = CacheForCanvas1.path8765737Rect;
            path8765737Rect.set(424.44f, 2117.49f, 482.17f, 2117.49f);
            Path path8765737Path = CacheForCanvas1.path8765737Path;
            path8765737Path.reset();
            path8765737Path.moveTo(482.17f, 2117.49f);
            path8765737Path.lineTo(424.44f, 2117.49f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765737Path, paint);
            canvas.restore();

            // path876-70-9-88
            RectF path87670988Rect = CacheForCanvas1.path87670988Rect;
            path87670988Rect.set(439.44f, 2143.47f, 497.17f, 2143.47f);
            Path path87670988Path = CacheForCanvas1.path87670988Path;
            path87670988Path.reset();
            path87670988Path.moveTo(497.17f, 2143.47f);
            path87670988Path.lineTo(439.44f, 2143.47f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87670988Path, paint);
            canvas.restore();

            // path876-3-9-2-60
            RectF path87639260Rect = CacheForCanvas1.path87639260Rect;
            path87639260Rect.set(441.94f, 2147.8f, 499.67f, 2147.8f);
            Path path87639260Path = CacheForCanvas1.path87639260Path;
            path87639260Path.reset();
            path87639260Path.moveTo(499.67f, 2147.8f);
            path87639260Path.lineTo(441.94f, 2147.8f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87639260Path, paint);
            canvas.restore();

            // path876-6-3-0-4
            RectF path8766304Rect = CacheForCanvas1.path8766304Rect;
            path8766304Rect.set(444.44f, 2152.13f, 502.17f, 2152.13f);
            Path path8766304Path = CacheForCanvas1.path8766304Path;
            path8766304Path.reset();
            path8766304Path.moveTo(502.17f, 2152.13f);
            path8766304Path.lineTo(444.44f, 2152.13f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766304Path, paint);
            canvas.restore();

            // path876-7-6-2-6
            RectF path8767626Rect = CacheForCanvas1.path8767626Rect;
            path8767626Rect.set(446.94f, 2156.46f, 504.67f, 2156.46f);
            Path path8767626Path = CacheForCanvas1.path8767626Path;
            path8767626Path.reset();
            path8767626Path.moveTo(504.67f, 2156.46f);
            path8767626Path.lineTo(446.94f, 2156.46f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767626Path, paint);
            canvas.restore();

            // path876-5-0-3-7
            RectF path8765037Rect = CacheForCanvas1.path8765037Rect;
            path8765037Rect.set(436.94f, 2139.14f, 494.67f, 2139.14f);
            Path path8765037Path = CacheForCanvas1.path8765037Path;
            path8765037Path.reset();
            path8765037Path.moveTo(494.67f, 2139.14f);
            path8765037Path.lineTo(436.94f, 2139.14f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765037Path, paint);
            canvas.restore();

            // rect1196-6
            RectF rect11966Rect = CacheForCanvas1.rect11966Rect;
            rect11966Rect.set(457.17f, 2074.19f, 510.45f, 2156.46f);
            Path rect11966Path = CacheForCanvas1.rect11966Path;
            rect11966Path.reset();
            rect11966Path.moveTo(457.17f, 2074.19f);
            rect11966Path.lineTo(462.95f, 2074.19f);
            rect11966Path.lineTo(510.45f, 2156.46f);
            rect11966Path.lineTo(504.67f, 2156.46f);
            rect11966Path.lineTo(457.17f, 2074.19f);
            rect11966Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor4);
            canvas.drawPath(rect11966Path, paint);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.07f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(rect11966Path, paint);
            canvas.restore();

            // path876-30-0
            RectF path876300Rect = CacheForCanvas1.path876300Rect;
            path876300Rect.set(552.05f, 2078.52f, 609.79f, 2078.52f);
            Path path876300Path = CacheForCanvas1.path876300Path;
            path876300Path.reset();
            path876300Path.moveTo(552.05f, 2078.52f);
            path876300Path.lineTo(609.79f, 2078.52f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876300Path, paint);
            canvas.restore();

            // path876-3-7-9
            RectF path876379Rect = CacheForCanvas1.path876379Rect;
            path876379Rect.set(554.55f, 2082.85f, 612.29f, 2082.85f);
            Path path876379Path = CacheForCanvas1.path876379Path;
            path876379Path.reset();
            path876379Path.moveTo(554.55f, 2082.85f);
            path876379Path.lineTo(612.29f, 2082.85f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876379Path, paint);
            canvas.restore();

            // path876-6-8-7
            RectF path876687Rect = CacheForCanvas1.path876687Rect;
            path876687Rect.set(557.05f, 2087.18f, 614.79f, 2087.18f);
            Path path876687Path = CacheForCanvas1.path876687Path;
            path876687Path.reset();
            path876687Path.moveTo(557.05f, 2087.18f);
            path876687Path.lineTo(614.79f, 2087.18f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876687Path, paint);
            canvas.restore();

            // path876-7-68-59
            RectF path87676859Rect = CacheForCanvas1.path87676859Rect;
            path87676859Rect.set(559.55f, 2091.51f, 617.29f, 2091.51f);
            Path path87676859Path = CacheForCanvas1.path87676859Path;
            path87676859Path.reset();
            path87676859Path.moveTo(559.55f, 2091.51f);
            path87676859Path.lineTo(617.29f, 2091.51f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87676859Path, paint);
            canvas.restore();

            // path876-5-8-78
            RectF path8765878Rect = CacheForCanvas1.path8765878Rect;
            path8765878Rect.set(549.55f, 2074.19f, 607.29f, 2074.19f);
            Path path8765878Path = CacheForCanvas1.path8765878Path;
            path8765878Path.reset();
            path8765878Path.moveTo(549.55f, 2074.19f);
            path8765878Path.lineTo(607.29f, 2074.19f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765878Path, paint);
            canvas.restore();

            // path876-70-4-53
            RectF path87670453Rect = CacheForCanvas1.path87670453Rect;
            path87670453Rect.set(564.55f, 2100.17f, 622.29f, 2100.17f);
            Path path87670453Path = CacheForCanvas1.path87670453Path;
            path87670453Path.reset();
            path87670453Path.moveTo(564.55f, 2100.17f);
            path87670453Path.lineTo(622.29f, 2100.17f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87670453Path, paint);
            canvas.restore();

            // path876-3-9-3-3
            RectF path8763933Rect = CacheForCanvas1.path8763933Rect;
            path8763933Rect.set(567.05f, 2104.5f, 624.79f, 2104.5f);
            Path path8763933Path = CacheForCanvas1.path8763933Path;
            path8763933Path.reset();
            path8763933Path.moveTo(567.05f, 2104.5f);
            path8763933Path.lineTo(624.79f, 2104.5f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763933Path, paint);
            canvas.restore();

            // path876-6-3-1-8
            RectF path8766318Rect = CacheForCanvas1.path8766318Rect;
            path8766318Rect.set(569.55f, 2108.83f, 627.29f, 2108.83f);
            Path path8766318Path = CacheForCanvas1.path8766318Path;
            path8766318Path.reset();
            path8766318Path.moveTo(569.55f, 2108.83f);
            path8766318Path.lineTo(627.29f, 2108.83f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766318Path, paint);
            canvas.restore();

            // path876-7-6-4-3
            RectF path8767643Rect = CacheForCanvas1.path8767643Rect;
            path8767643Rect.set(572.05f, 2113.16f, 629.79f, 2113.16f);
            Path path8767643Path = CacheForCanvas1.path8767643Path;
            path8767643Path.reset();
            path8767643Path.moveTo(572.05f, 2113.16f);
            path8767643Path.lineTo(629.79f, 2113.16f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767643Path, paint);
            canvas.restore();

            // path876-5-0-9-79
            RectF path87650979Rect = CacheForCanvas1.path87650979Rect;
            path87650979Rect.set(562.05f, 2095.84f, 619.79f, 2095.84f);
            Path path87650979Path = CacheForCanvas1.path87650979Path;
            path87650979Path.reset();
            path87650979Path.moveTo(562.05f, 2095.84f);
            path87650979Path.lineTo(619.79f, 2095.84f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87650979Path, paint);
            canvas.restore();

            // path876-62-2-3
            RectF path8766223Rect = CacheForCanvas1.path8766223Rect;
            path8766223Rect.set(577.05f, 2121.82f, 634.79f, 2121.82f);
            Path path8766223Path = CacheForCanvas1.path8766223Path;
            path8766223Path.reset();
            path8766223Path.moveTo(577.05f, 2121.82f);
            path8766223Path.lineTo(634.79f, 2121.82f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766223Path, paint);
            canvas.restore();

            // path876-3-6-0-7
            RectF path8763607Rect = CacheForCanvas1.path8763607Rect;
            path8763607Rect.set(579.55f, 2126.15f, 637.29f, 2126.15f);
            Path path8763607Path = CacheForCanvas1.path8763607Path;
            path8763607Path.reset();
            path8763607Path.moveTo(579.55f, 2126.15f);
            path8763607Path.lineTo(637.29f, 2126.15f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763607Path, paint);
            canvas.restore();

            // path876-6-1-6-87
            RectF path87661687Rect = CacheForCanvas1.path87661687Rect;
            path87661687Rect.set(582.05f, 2130.48f, 639.79f, 2130.48f);
            Path path87661687Path = CacheForCanvas1.path87661687Path;
            path87661687Path.reset();
            path87661687Path.moveTo(582.05f, 2130.48f);
            path87661687Path.lineTo(639.79f, 2130.48f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87661687Path, paint);
            canvas.restore();

            // path876-7-8-8-41
            RectF path87678841Rect = CacheForCanvas1.path87678841Rect;
            path87678841Rect.set(584.55f, 2134.81f, 642.29f, 2134.81f);
            Path path87678841Path = CacheForCanvas1.path87678841Path;
            path87678841Path.reset();
            path87678841Path.moveTo(584.55f, 2134.81f);
            path87678841Path.lineTo(642.29f, 2134.81f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87678841Path, paint);
            canvas.restore();

            // path876-5-7-9-9
            RectF path8765799Rect = CacheForCanvas1.path8765799Rect;
            path8765799Rect.set(574.55f, 2117.49f, 632.29f, 2117.49f);
            Path path8765799Path = CacheForCanvas1.path8765799Path;
            path8765799Path.reset();
            path8765799Path.moveTo(574.55f, 2117.49f);
            path8765799Path.lineTo(632.29f, 2117.49f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765799Path, paint);
            canvas.restore();

            // path876-70-9-2-0
            RectF path87670920Rect = CacheForCanvas1.path87670920Rect;
            path87670920Rect.set(589.55f, 2143.47f, 647.29f, 2143.47f);
            Path path87670920Path = CacheForCanvas1.path87670920Path;
            path87670920Path.reset();
            path87670920Path.moveTo(589.55f, 2143.47f);
            path87670920Path.lineTo(647.29f, 2143.47f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87670920Path, paint);
            canvas.restore();

            // path876-3-9-2-6-98
            RectF path876392698Rect = CacheForCanvas1.path876392698Rect;
            path876392698Rect.set(592.05f, 2147.8f, 649.79f, 2147.8f);
            Path path876392698Path = CacheForCanvas1.path876392698Path;
            path876392698Path.reset();
            path876392698Path.moveTo(592.05f, 2147.8f);
            path876392698Path.lineTo(649.79f, 2147.8f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876392698Path, paint);
            canvas.restore();

            // path876-6-3-0-6-8
            RectF path87663068Rect = CacheForCanvas1.path87663068Rect;
            path87663068Rect.set(594.55f, 2152.13f, 652.29f, 2152.13f);
            Path path87663068Path = CacheForCanvas1.path87663068Path;
            path87663068Path.reset();
            path87663068Path.moveTo(594.55f, 2152.13f);
            path87663068Path.lineTo(652.29f, 2152.13f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87663068Path, paint);
            canvas.restore();

            // path876-7-6-2-4-5
            RectF path87676245Rect = CacheForCanvas1.path87676245Rect;
            path87676245Rect.set(597.05f, 2156.46f, 654.79f, 2156.46f);
            Path path87676245Path = CacheForCanvas1.path87676245Path;
            path87676245Path.reset();
            path87676245Path.moveTo(597.05f, 2156.46f);
            path87676245Path.lineTo(654.79f, 2156.46f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87676245Path, paint);
            canvas.restore();

            // path876-5-0-3-9-8
            RectF path87650398Rect = CacheForCanvas1.path87650398Rect;
            path87650398Rect.set(587.05f, 2139.14f, 644.79f, 2139.14f);
            Path path87650398Path = CacheForCanvas1.path87650398Path;
            path87650398Path.reset();
            path87650398Path.moveTo(587.05f, 2139.14f);
            path87650398Path.lineTo(644.79f, 2139.14f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87650398Path, paint);
            canvas.restore();

            // rect1196-5-4
            RectF rect119654Rect = CacheForCanvas1.rect119654Rect;
            rect119654Rect.set(543.78f, 2074.19f, 597.05f, 2156.46f);
            Path rect119654Path = CacheForCanvas1.rect119654Path;
            rect119654Path.reset();
            rect119654Path.moveTo(549.55f, 2074.19f);
            rect119654Path.lineTo(543.78f, 2074.19f);
            rect119654Path.lineTo(591.28f, 2156.46f);
            rect119654Path.lineTo(597.05f, 2156.46f);
            rect119654Path.lineTo(549.55f, 2074.19f);
            rect119654Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor4);
            canvas.drawPath(rect119654Path, paint);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.07f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(rect119654Path, paint);
            canvas.restore();

            // path876-1-37
            RectF path876137Rect = CacheForCanvas1.path876137Rect;
            path876137Rect.set(465.45f, 2078.52f, 546.28f, 2078.52f);
            Path path876137Path = CacheForCanvas1.path876137Path;
            path876137Path.reset();
            path876137Path.moveTo(546.28f, 2078.52f);
            path876137Path.lineTo(465.45f, 2078.52f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876137Path, paint);
            canvas.restore();

            // path876-3-1-1
            RectF path876311Rect = CacheForCanvas1.path876311Rect;
            path876311Rect.set(467.95f, 2082.85f, 548.78f, 2082.85f);
            Path path876311Path = CacheForCanvas1.path876311Path;
            path876311Path.reset();
            path876311Path.moveTo(548.78f, 2082.85f);
            path876311Path.lineTo(467.95f, 2082.85f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876311Path, paint);
            canvas.restore();

            // path876-6-5-3
            RectF path876653Rect = CacheForCanvas1.path876653Rect;
            path876653Rect.set(470.45f, 2087.18f, 551.28f, 2087.18f);
            Path path876653Path = CacheForCanvas1.path876653Path;
            path876653Path.reset();
            path876653Path.moveTo(551.28f, 2087.18f);
            path876653Path.lineTo(470.45f, 2087.18f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876653Path, paint);
            canvas.restore();

            // path876-7-9-8
            RectF path876798Rect = CacheForCanvas1.path876798Rect;
            path876798Rect.set(472.95f, 2091.51f, 553.78f, 2091.51f);
            Path path876798Path = CacheForCanvas1.path876798Path;
            path876798Path.reset();
            path876798Path.moveTo(553.78f, 2091.51f);
            path876798Path.lineTo(472.95f, 2091.51f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876798Path, paint);
            canvas.restore();

            // path876-5-77-0
            RectF path8765770Rect = CacheForCanvas1.path8765770Rect;
            path8765770Rect.set(462.95f, 2074.19f, 543.78f, 2074.19f);
            Path path8765770Path = CacheForCanvas1.path8765770Path;
            path8765770Path.reset();
            path8765770Path.moveTo(543.78f, 2074.19f);
            path8765770Path.lineTo(462.95f, 2074.19f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765770Path, paint);
            canvas.restore();

            // path876-70-6-9
            RectF path8767069Rect = CacheForCanvas1.path8767069Rect;
            path8767069Rect.set(477.95f, 2100.17f, 558.78f, 2100.17f);
            Path path8767069Path = CacheForCanvas1.path8767069Path;
            path8767069Path.reset();
            path8767069Path.moveTo(558.78f, 2100.17f);
            path8767069Path.lineTo(477.95f, 2100.17f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767069Path, paint);
            canvas.restore();

            // path876-3-9-7-7
            RectF path8763977Rect = CacheForCanvas1.path8763977Rect;
            path8763977Rect.set(480.45f, 2104.5f, 561.28f, 2104.5f);
            Path path8763977Path = CacheForCanvas1.path8763977Path;
            path8763977Path.reset();
            path8763977Path.moveTo(561.28f, 2104.5f);
            path8763977Path.lineTo(480.45f, 2104.5f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763977Path, paint);
            canvas.restore();

            // path876-6-3-3-9
            RectF path8766339Rect = CacheForCanvas1.path8766339Rect;
            path8766339Rect.set(482.95f, 2108.83f, 563.78f, 2108.83f);
            Path path8766339Path = CacheForCanvas1.path8766339Path;
            path8766339Path.reset();
            path8766339Path.moveTo(563.78f, 2108.83f);
            path8766339Path.lineTo(482.95f, 2108.83f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766339Path, paint);
            canvas.restore();

            // path876-7-6-6-93
            RectF path87676693Rect = CacheForCanvas1.path87676693Rect;
            path87676693Rect.set(485.45f, 2113.16f, 566.28f, 2113.16f);
            Path path87676693Path = CacheForCanvas1.path87676693Path;
            path87676693Path.reset();
            path87676693Path.moveTo(566.28f, 2113.16f);
            path87676693Path.lineTo(485.45f, 2113.16f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87676693Path, paint);
            canvas.restore();

            // path876-5-0-5-2
            RectF path8765052Rect = CacheForCanvas1.path8765052Rect;
            path8765052Rect.set(475.45f, 2095.84f, 556.28f, 2095.84f);
            Path path8765052Path = CacheForCanvas1.path8765052Path;
            path8765052Path.reset();
            path8765052Path.moveTo(556.28f, 2095.84f);
            path8765052Path.lineTo(475.45f, 2095.84f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765052Path, paint);
            canvas.restore();

            // path876-62-6-4
            RectF path8766264Rect = CacheForCanvas1.path8766264Rect;
            path8766264Rect.set(490.45f, 2121.82f, 571.28f, 2121.82f);
            Path path8766264Path = CacheForCanvas1.path8766264Path;
            path8766264Path.reset();
            path8766264Path.moveTo(571.28f, 2121.82f);
            path8766264Path.lineTo(490.45f, 2121.82f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766264Path, paint);
            canvas.restore();

            // path876-3-6-3-37
            RectF path87636337Rect = CacheForCanvas1.path87636337Rect;
            path87636337Rect.set(492.95f, 2126.15f, 573.78f, 2126.15f);
            Path path87636337Path = CacheForCanvas1.path87636337Path;
            path87636337Path.reset();
            path87636337Path.moveTo(573.78f, 2126.15f);
            path87636337Path.lineTo(492.95f, 2126.15f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87636337Path, paint);
            canvas.restore();

            // path876-6-1-9-12
            RectF path87661912Rect = CacheForCanvas1.path87661912Rect;
            path87661912Rect.set(495.45f, 2130.48f, 576.28f, 2130.48f);
            Path path87661912Path = CacheForCanvas1.path87661912Path;
            path87661912Path.reset();
            path87661912Path.moveTo(576.28f, 2130.48f);
            path87661912Path.lineTo(495.45f, 2130.48f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87661912Path, paint);
            canvas.restore();

            // path876-7-8-4-2
            RectF path8767842Rect = CacheForCanvas1.path8767842Rect;
            path8767842Rect.set(497.95f, 2134.81f, 578.78f, 2134.81f);
            Path path8767842Path = CacheForCanvas1.path8767842Path;
            path8767842Path.reset();
            path8767842Path.moveTo(578.78f, 2134.81f);
            path8767842Path.lineTo(497.95f, 2134.81f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767842Path, paint);
            canvas.restore();

            // path876-5-7-8-02
            RectF path87657802Rect = CacheForCanvas1.path87657802Rect;
            path87657802Rect.set(487.95f, 2117.49f, 568.78f, 2117.49f);
            Path path87657802Path = CacheForCanvas1.path87657802Path;
            path87657802Path.reset();
            path87657802Path.moveTo(568.78f, 2117.49f);
            path87657802Path.lineTo(487.95f, 2117.49f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87657802Path, paint);
            canvas.restore();

            // path876-70-9-1-1
            RectF path87670911Rect = CacheForCanvas1.path87670911Rect;
            path87670911Rect.set(502.95f, 2143.47f, 583.78f, 2143.47f);
            Path path87670911Path = CacheForCanvas1.path87670911Path;
            path87670911Path.reset();
            path87670911Path.moveTo(583.78f, 2143.47f);
            path87670911Path.lineTo(502.95f, 2143.47f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87670911Path, paint);
            canvas.restore();

            // path876-3-9-2-2-75
            RectF path876392275Rect = CacheForCanvas1.path876392275Rect;
            path876392275Rect.set(505.45f, 2147.8f, 586.28f, 2147.8f);
            Path path876392275Path = CacheForCanvas1.path876392275Path;
            path876392275Path.reset();
            path876392275Path.moveTo(586.28f, 2147.8f);
            path876392275Path.lineTo(505.45f, 2147.8f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876392275Path, paint);
            canvas.restore();

            // path876-6-3-0-9-1
            RectF path87663091Rect = CacheForCanvas1.path87663091Rect;
            path87663091Rect.set(507.95f, 2152.13f, 588.78f, 2152.13f);
            Path path87663091Path = CacheForCanvas1.path87663091Path;
            path87663091Path.reset();
            path87663091Path.moveTo(588.78f, 2152.13f);
            path87663091Path.lineTo(507.95f, 2152.13f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87663091Path, paint);
            canvas.restore();

            // path876-7-6-2-3-7
            RectF path87676237Rect = CacheForCanvas1.path87676237Rect;
            path87676237Rect.set(510.45f, 2156.46f, 591.28f, 2156.46f);
            Path path87676237Path = CacheForCanvas1.path87676237Path;
            path87676237Path.reset();
            path87676237Path.moveTo(591.28f, 2156.46f);
            path87676237Path.lineTo(510.45f, 2156.46f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87676237Path, paint);
            canvas.restore();

            // path876-5-0-3-90-4
            RectF path876503904Rect = CacheForCanvas1.path876503904Rect;
            path876503904Rect.set(500.45f, 2139.14f, 581.28f, 2139.14f);
            Path path876503904Path = CacheForCanvas1.path876503904Path;
            path876503904Path.reset();
            path876503904Path.moveTo(581.28f, 2139.14f);
            path876503904Path.lineTo(500.45f, 2139.14f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876503904Path, paint);
            canvas.restore();

            // rect1446-1
            RectF rect14461Rect = CacheForCanvas1.rect14461Rect;
            rect14461Rect.set(390.27f, 1918.3f, 473f, 1961.6f);
            Path rect14461Path = CacheForCanvas1.rect14461Path;
            rect14461Path.reset();
            rect14461Path.moveTo(390.27f, 1918.3f);
            rect14461Path.lineTo(448f, 1918.3f);
            rect14461Path.lineTo(473f, 1961.6f);
            rect14461Path.lineTo(415.27f, 1961.6f);
            rect14461Path.lineTo(390.27f, 1918.3f);
            rect14461Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor4);
            canvas.drawPath(rect14461Path, paint);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.07f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(rect14461Path, paint);
            canvas.restore();

            // path1459-7
            RectF path14597Rect = CacheForCanvas1.path14597Rect;
            path14597Rect.set(668.58f, 1840.36f, 956.79f, 2039.55f);
            Path path14597Path = CacheForCanvas1.path14597Path;
            path14597Path.reset();
            path14597Path.moveTo(668.58f, 1840.36f);
            path14597Path.lineTo(841.79f, 1840.36f);
            path14597Path.moveTo(956.79f, 2039.55f);
            path14597Path.lineTo(783.58f, 2039.55f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path14597Path, paint);
            canvas.restore();

            // path1461-1
            RectF path14611Rect = CacheForCanvas1.path14611Rect;
            path14611Rect.set(688.58f, 1875f, 936.79f, 2004.91f);
            Path path14611Path = CacheForCanvas1.path14611Path;
            path14611Path.reset();
            path14611Path.moveTo(763.58f, 2004.91f);
            path14611Path.lineTo(936.79f, 2004.91f);
            path14611Path.lineTo(861.79f, 1875f);
            path14611Path.lineTo(688.58f, 1875f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path14611Path, paint);
            canvas.restore();

            // rect1519-1
            RectF rect15191Rect = CacheForCanvas1.rect15191Rect;
            rect15191Rect.set(821.79f, 1805.72f, 1415.58f, 2074.19f);
            Path rect15191Path = CacheForCanvas1.rect15191Path;
            rect15191Path.reset();
            rect15191Path.moveTo(821.79f, 1805.72f);
            rect15191Path.lineTo(1260.58f, 1805.72f);
            rect15191Path.lineTo(1415.58f, 2074.19f);
            rect15191Path.lineTo(976.79f, 2074.19f);
            rect15191Path.lineTo(821.79f, 1805.72f);
            rect15191Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor5);
            canvas.drawPath(rect15191Path, paint);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(4.03f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(rect15191Path, paint);
            canvas.restore();

            // path830-8-0
            RectF path83080Rect = CacheForCanvas1.path83080Rect;
            path83080Rect.set(216.94f, 1758.09f, 394.44f, 2065.53f);
            Path path83080Path = CacheForCanvas1.path83080Path;
            path83080Path.reset();
            path83080Path.moveTo(394.44f, 2065.53f);
            path83080Path.lineTo(216.94f, 1758.09f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3.78f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path83080Path, paint);
            canvas.restore();

            // path876-8-4
            RectF path87684Rect = CacheForCanvas1.path87684Rect;
            path87684Rect.set(244.44f, 1805.72f, 302.17f, 1805.72f);
            Path path87684Path = CacheForCanvas1.path87684Path;
            path87684Path.reset();
            path87684Path.moveTo(302.17f, 1805.72f);
            path87684Path.lineTo(244.44f, 1805.72f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87684Path, paint);
            canvas.restore();

            // path876-3-5-0
            RectF path876350Rect = CacheForCanvas1.path876350Rect;
            path876350Rect.set(241.94f, 1801.39f, 299.67f, 1801.39f);
            Path path876350Path = CacheForCanvas1.path876350Path;
            path876350Path.reset();
            path876350Path.moveTo(299.67f, 1801.39f);
            path876350Path.lineTo(241.94f, 1801.39f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876350Path, paint);
            canvas.restore();

            // path876-6-0-8
            RectF path876608Rect = CacheForCanvas1.path876608Rect;
            path876608Rect.set(239.44f, 1797.06f, 297.17f, 1797.06f);
            Path path876608Path = CacheForCanvas1.path876608Path;
            path876608Path.reset();
            path876608Path.moveTo(297.17f, 1797.06f);
            path876608Path.lineTo(239.44f, 1797.06f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876608Path, paint);
            canvas.restore();

            // path876-7-96-5
            RectF path8767965Rect = CacheForCanvas1.path8767965Rect;
            path8767965Rect.set(236.94f, 1792.73f, 294.67f, 1792.73f);
            Path path8767965Path = CacheForCanvas1.path8767965Path;
            path8767965Path.reset();
            path8767965Path.moveTo(294.67f, 1792.73f);
            path8767965Path.lineTo(236.94f, 1792.73f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767965Path, paint);
            canvas.restore();

            // path876-5-3-1
            RectF path876531Rect = CacheForCanvas1.path876531Rect;
            path876531Rect.set(246.94f, 1810.05f, 304.67f, 1810.05f);
            Path path876531Path = CacheForCanvas1.path876531Path;
            path876531Path.reset();
            path876531Path.moveTo(304.67f, 1810.05f);
            path876531Path.lineTo(246.94f, 1810.05f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876531Path, paint);
            canvas.restore();

            // path876-70-8-6
            RectF path8767086Rect = CacheForCanvas1.path8767086Rect;
            path8767086Rect.set(231.94f, 1784.07f, 289.67f, 1784.07f);
            Path path8767086Path = CacheForCanvas1.path8767086Path;
            path8767086Path.reset();
            path8767086Path.moveTo(289.67f, 1784.07f);
            path8767086Path.lineTo(231.94f, 1784.07f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767086Path, paint);
            canvas.restore();

            // path876-3-9-5-6
            RectF path8763956Rect = CacheForCanvas1.path8763956Rect;
            path8763956Rect.set(229.44f, 1779.74f, 287.17f, 1779.74f);
            Path path8763956Path = CacheForCanvas1.path8763956Path;
            path8763956Path.reset();
            path8763956Path.moveTo(287.17f, 1779.74f);
            path8763956Path.lineTo(229.44f, 1779.74f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763956Path, paint);
            canvas.restore();

            // path876-6-3-6-2
            RectF path8766362Rect = CacheForCanvas1.path8766362Rect;
            path8766362Rect.set(226.94f, 1775.41f, 284.67f, 1775.41f);
            Path path8766362Path = CacheForCanvas1.path8766362Path;
            path8766362Path.reset();
            path8766362Path.moveTo(284.67f, 1775.41f);
            path8766362Path.lineTo(226.94f, 1775.41f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766362Path, paint);
            canvas.restore();

            // path876-7-6-1-1
            RectF path8767611Rect = CacheForCanvas1.path8767611Rect;
            path8767611Rect.set(224.44f, 1771.08f, 282.17f, 1771.08f);
            Path path8767611Path = CacheForCanvas1.path8767611Path;
            path8767611Path.reset();
            path8767611Path.moveTo(282.17f, 1771.08f);
            path8767611Path.lineTo(224.44f, 1771.08f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767611Path, paint);
            canvas.restore();

            // path876-5-0-1-9
            RectF path8765019Rect = CacheForCanvas1.path8765019Rect;
            path8765019Rect.set(234.44f, 1788.4f, 292.17f, 1788.4f);
            Path path8765019Path = CacheForCanvas1.path8765019Path;
            path8765019Path.reset();
            path8765019Path.moveTo(292.17f, 1788.4f);
            path8765019Path.lineTo(234.44f, 1788.4f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765019Path, paint);
            canvas.restore();

            // path876-62-5-6
            RectF path8766256Rect = CacheForCanvas1.path8766256Rect;
            path8766256Rect.set(219.44f, 1762.42f, 277.17f, 1762.42f);
            Path path8766256Path = CacheForCanvas1.path8766256Path;
            path8766256Path.reset();
            path8766256Path.moveTo(277.17f, 1762.42f);
            path8766256Path.lineTo(219.44f, 1762.42f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766256Path, paint);
            canvas.restore();

            // path876-3-6-9- 2
            RectF path8763692Rect = CacheForCanvas1.path8763692Rect;
            path8763692Rect.set(216.94f, 1758.09f, 274.67f, 1758.09f);
            Path path8763692Path = CacheForCanvas1.path8763692Path;
            path8763692Path.reset();
            path8763692Path.moveTo(274.67f, 1758.09f);
            path8763692Path.lineTo(216.94f, 1758.09f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763692Path, paint);
            canvas.restore();

            // path876-6-1-8-8
            RectF path8766188Rect = CacheForCanvas1.path8766188Rect;
            path8766188Rect.set(214.44f, 1753.76f, 272.17f, 1753.76f);
            Path path8766188Path = CacheForCanvas1.path8766188Path;
            path8766188Path.reset();
            path8766188Path.moveTo(272.17f, 1753.76f);
            path8766188Path.lineTo(214.44f, 1753.76f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766188Path, paint);
            canvas.restore();

            // path876-7-8-48-0
            RectF path87678480Rect = CacheForCanvas1.path87678480Rect;
            path87678480Rect.set(211.94f, 1749.43f, 269.67f, 1749.43f);
            Path path87678480Path = CacheForCanvas1.path87678480Path;
            path87678480Path.reset();
            path87678480Path.moveTo(269.67f, 1749.43f);
            path87678480Path.lineTo(211.94f, 1749.43f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87678480Path, paint);
            canvas.restore();

            // path876-5-7-1-8
            RectF path8765718Rect = CacheForCanvas1.path8765718Rect;
            path8765718Rect.set(221.94f, 1766.75f, 279.67f, 1766.75f);
            Path path8765718Path = CacheForCanvas1.path8765718Path;
            path8765718Path.reset();
            path8765718Path.moveTo(279.67f, 1766.75f);
            path8765718Path.lineTo(221.94f, 1766.75f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765718Path, paint);
            canvas.restore();

            // path876-70-9-0-1
            RectF path87670901Rect = CacheForCanvas1.path87670901Rect;
            path87670901Rect.set(206.94f, 1740.77f, 264.67f, 1740.77f);
            Path path87670901Path = CacheForCanvas1.path87670901Path;
            path87670901Path.reset();
            path87670901Path.moveTo(264.67f, 1740.77f);
            path87670901Path.lineTo(206.94f, 1740.77f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87670901Path, paint);
            canvas.restore();

            // path876-3-9-2-3-0
            RectF path87639230Rect = CacheForCanvas1.path87639230Rect;
            path87639230Rect.set(204.44f, 1736.44f, 262.17f, 1736.44f);
            Path path87639230Path = CacheForCanvas1.path87639230Path;
            path87639230Path.reset();
            path87639230Path.moveTo(262.17f, 1736.44f);
            path87639230Path.lineTo(204.44f, 1736.44f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87639230Path, paint);
            canvas.restore();

            // path876-6-3-0-0- 3
            RectF path87663003Rect = CacheForCanvas1.path87663003Rect;
            path87663003Rect.set(201.94f, 1732.11f, 259.67f, 1732.11f);
            Path path87663003Path = CacheForCanvas1.path87663003Path;
            path87663003Path.reset();
            path87663003Path.moveTo(259.67f, 1732.11f);
            path87663003Path.lineTo(201.94f, 1732.11f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87663003Path, paint);
            canvas.restore();

            // path876-7-6-2-44-2
            RectF path876762442Rect = CacheForCanvas1.path876762442Rect;
            path876762442Rect.set(199.44f, 1727.78f, 257.17f, 1727.78f);
            Path path876762442Path = CacheForCanvas1.path876762442Path;
            path876762442Path.reset();
            path876762442Path.moveTo(257.17f, 1727.78f);
            path876762442Path.lineTo(199.44f, 1727.78f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876762442Path, paint);
            canvas.restore();

            // path876-5-0-3-4-9
            RectF path87650349Rect = CacheForCanvas1.path87650349Rect;
            path87650349Rect.set(209.44f, 1745.1f, 267.17f, 1745.1f);
            Path path87650349Path = CacheForCanvas1.path87650349Path;
            path87650349Path.reset();
            path87650349Path.moveTo(267.17f, 1745.1f);
            path87650349Path.lineTo(209.44f, 1745.1f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87650349Path, paint);
            canvas.restore();

            // rect1196-4-75
            RectF rect1196475Rect = CacheForCanvas1.rect1196475Rect;
            rect1196475Rect.set(257.17f, 1727.78f, 310.45f, 1810.05f);
            Path rect1196475Path = CacheForCanvas1.rect1196475Path;
            rect1196475Path.reset();
            rect1196475Path.moveTo(304.67f, 1810.05f);
            rect1196475Path.lineTo(310.45f, 1810.05f);
            rect1196475Path.lineTo(262.95f, 1727.78f);
            rect1196475Path.lineTo(257.17f, 1727.78f);
            rect1196475Path.lineTo(304.67f, 1810.05f);
            rect1196475Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor4);
            canvas.drawPath(rect1196475Path, paint);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.07f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(rect1196475Path, paint);
            canvas.restore();

            // path876-30-7-6
            RectF path8763076Rect = CacheForCanvas1.path8763076Rect;
            path8763076Rect.set(394.55f, 1805.72f, 452.29f, 1805.72f);
            Path path8763076Path = CacheForCanvas1.path8763076Path;
            path8763076Path.reset();
            path8763076Path.moveTo(394.55f, 1805.72f);
            path8763076Path.lineTo(452.29f, 1805.72f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763076Path, paint);
            canvas.restore();

            // path876-3-7-6-4
            RectF path8763764Rect = CacheForCanvas1.path8763764Rect;
            path8763764Rect.set(392.05f, 1801.39f, 449.79f, 1801.39f);
            Path path8763764Path = CacheForCanvas1.path8763764Path;
            path8763764Path.reset();
            path8763764Path.moveTo(392.05f, 1801.39f);
            path8763764Path.lineTo(449.79f, 1801.39f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763764Path, paint);
            canvas.restore();

            // path876-6-8-3-6
            RectF path8766836Rect = CacheForCanvas1.path8766836Rect;
            path8766836Rect.set(389.55f, 1797.06f, 447.29f, 1797.06f);
            Path path8766836Path = CacheForCanvas1.path8766836Path;
            path8766836Path.reset();
            path8766836Path.moveTo(389.55f, 1797.06f);
            path8766836Path.lineTo(447.29f, 1797.06f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766836Path, paint);
            canvas.restore();

            // path876-7-68-1-3
            RectF path87676813Rect = CacheForCanvas1.path87676813Rect;
            path87676813Rect.set(387.05f, 1792.73f, 444.79f, 1792.73f);
            Path path87676813Path = CacheForCanvas1.path87676813Path;
            path87676813Path.reset();
            path87676813Path.moveTo(387.05f, 1792.73f);
            path87676813Path.lineTo(444.79f, 1792.73f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87676813Path, paint);
            canvas.restore();

            // path876-5-8-7-7
            RectF path8765877Rect = CacheForCanvas1.path8765877Rect;
            path8765877Rect.set(397.05f, 1810.05f, 454.79f, 1810.05f);
            Path path8765877Path = CacheForCanvas1.path8765877Path;
            path8765877Path.reset();
            path8765877Path.moveTo(397.05f, 1810.05f);
            path8765877Path.lineTo(454.79f, 1810.05f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765877Path, paint);
            canvas.restore();

            // path876-70-4-5-9
            RectF path87670459Rect = CacheForCanvas1.path87670459Rect;
            path87670459Rect.set(382.05f, 1784.07f, 439.79f, 1784.07f);
            Path path87670459Path = CacheForCanvas1.path87670459Path;
            path87670459Path.reset();
            path87670459Path.moveTo(382.05f, 1784.07f);
            path87670459Path.lineTo(439.79f, 1784.07f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87670459Path, paint);
            canvas.restore();

            // path876-3-9-3-9-7
            RectF path87639397Rect = CacheForCanvas1.path87639397Rect;
            path87639397Rect.set(379.55f, 1779.74f, 437.29f, 1779.74f);
            Path path87639397Path = CacheForCanvas1.path87639397Path;
            path87639397Path.reset();
            path87639397Path.moveTo(379.55f, 1779.74f);
            path87639397Path.lineTo(437.29f, 1779.74f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87639397Path, paint);
            canvas.restore();

            // path876-6-3-1-6-4
            RectF path87663164Rect = CacheForCanvas1.path87663164Rect;
            path87663164Rect.set(377.05f, 1775.41f, 434.79f, 1775.41f);
            Path path87663164Path = CacheForCanvas1.path87663164Path;
            path87663164Path.reset();
            path87663164Path.moveTo(377.05f, 1775.41f);
            path87663164Path.lineTo(434.79f, 1775.41f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87663164Path, paint);
            canvas.restore();

            // path876-7-6-4-2-9
            RectF path87676429Rect = CacheForCanvas1.path87676429Rect;
            path87676429Rect.set(374.55f, 1771.08f, 432.29f, 1771.08f);
            Path path87676429Path = CacheForCanvas1.path87676429Path;
            path87676429Path.reset();
            path87676429Path.moveTo(374.55f, 1771.08f);
            path87676429Path.lineTo(432.29f, 1771.08f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87676429Path, paint);
            canvas.restore();

            // path876-5-0-9-1-1
            RectF path87650911Rect = CacheForCanvas1.path87650911Rect;
            path87650911Rect.set(384.55f, 1788.4f, 442.29f, 1788.4f);
            Path path87650911Path = CacheForCanvas1.path87650911Path;
            path87650911Path.reset();
            path87650911Path.moveTo(384.55f, 1788.4f);
            path87650911Path.lineTo(442.29f, 1788.4f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87650911Path, paint);
            canvas.restore();

            // path876-62-2-7-7
            RectF path87662277Rect = CacheForCanvas1.path87662277Rect;
            path87662277Rect.set(369.55f, 1762.42f, 427.29f, 1762.42f);
            Path path87662277Path = CacheForCanvas1.path87662277Path;
            path87662277Path.reset();
            path87662277Path.moveTo(369.55f, 1762.42f);
            path87662277Path.lineTo(427.29f, 1762.42f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87662277Path, paint);
            canvas.restore();

            // path876-3-6-0-8-0
            RectF path87636080Rect = CacheForCanvas1.path87636080Rect;
            path87636080Rect.set(367.05f, 1758.09f, 424.79f, 1758.09f);
            Path path87636080Path = CacheForCanvas1.path87636080Path;
            path87636080Path.reset();
            path87636080Path.moveTo(367.05f, 1758.09f);
            path87636080Path.lineTo(424.79f, 1758.09f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87636080Path, paint);
            canvas.restore();

            // path876-6-1-6-5-6
            RectF path87661656Rect = CacheForCanvas1.path87661656Rect;
            path87661656Rect.set(364.55f, 1753.76f, 422.29f, 1753.76f);
            Path path87661656Path = CacheForCanvas1.path87661656Path;
            path87661656Path.reset();
            path87661656Path.moveTo(364.55f, 1753.76f);
            path87661656Path.lineTo(422.29f, 1753.76f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87661656Path, paint);
            canvas.restore();

            // path876-7-8-8-7-0
            RectF path87678870Rect = CacheForCanvas1.path87678870Rect;
            path87678870Rect.set(362.05f, 1749.43f, 419.79f, 1749.43f);
            Path path87678870Path = CacheForCanvas1.path87678870Path;
            path87678870Path.reset();
            path87678870Path.moveTo(362.05f, 1749.43f);
            path87678870Path.lineTo(419.79f, 1749.43f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87678870Path, paint);
            canvas.restore();

            // path876-5-7-9-4-8
            RectF path87657948Rect = CacheForCanvas1.path87657948Rect;
            path87657948Rect.set(372.05f, 1766.75f, 429.79f, 1766.75f);
            Path path87657948Path = CacheForCanvas1.path87657948Path;
            path87657948Path.reset();
            path87657948Path.moveTo(372.05f, 1766.75f);
            path87657948Path.lineTo(429.79f, 1766.75f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87657948Path, paint);
            canvas.restore();

            // path876-70-9-2-1-5
            RectF path876709215Rect = CacheForCanvas1.path876709215Rect;
            path876709215Rect.set(357.05f, 1740.77f, 414.79f, 1740.77f);
            Path path876709215Path = CacheForCanvas1.path876709215Path;
            path876709215Path.reset();
            path876709215Path.moveTo(357.05f, 1740.77f);
            path876709215Path.lineTo(414.79f, 1740.77f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876709215Path, paint);
            canvas.restore();

            // path876-3-9-2-6-8-3
            RectF path876392683Rect = CacheForCanvas1.path876392683Rect;
            path876392683Rect.set(354.55f, 1736.44f, 412.29f, 1736.44f);
            Path path876392683Path = CacheForCanvas1.path876392683Path;
            path876392683Path.reset();
            path876392683Path.moveTo(354.55f, 1736.44f);
            path876392683Path.lineTo(412.29f, 1736.44f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876392683Path, paint);
            canvas.restore();

            // path876-6-3-0-6-5-9
            RectF path876630659Rect = CacheForCanvas1.path876630659Rect;
            path876630659Rect.set(352.05f, 1732.11f, 409.79f, 1732.11f);
            Path path876630659Path = CacheForCanvas1.path876630659Path;
            path876630659Path.reset();
            path876630659Path.moveTo(352.05f, 1732.11f);
            path876630659Path.lineTo(409.79f, 1732.11f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876630659Path, paint);
            canvas.restore();

            // path876-7-6-2-4-9-4
            RectF path876762494Rect = CacheForCanvas1.path876762494Rect;
            path876762494Rect.set(349.55f, 1727.78f, 407.29f, 1727.78f);
            Path path876762494Path = CacheForCanvas1.path876762494Path;
            path876762494Path.reset();
            path876762494Path.moveTo(349.55f, 1727.78f);
            path876762494Path.lineTo(407.29f, 1727.78f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876762494Path, paint);
            canvas.restore();

            // path876-5-0-3-9-7-15
            RectF path8765039715Rect = CacheForCanvas1.path8765039715Rect;
            path8765039715Rect.set(359.55f, 1745.1f, 417.29f, 1745.1f);
            Path path8765039715Path = CacheForCanvas1.path8765039715Path;
            path8765039715Path.reset();
            path8765039715Path.moveTo(359.55f, 1745.1f);
            path8765039715Path.lineTo(417.29f, 1745.1f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765039715Path, paint);
            canvas.restore();

            // rect1196-5-5-4
            RectF rect1196554Rect = CacheForCanvas1.rect1196554Rect;
            rect1196554Rect.set(343.78f, 1727.78f, 397.05f, 1810.05f);
            Path rect1196554Path = CacheForCanvas1.rect1196554Path;
            rect1196554Path.reset();
            rect1196554Path.moveTo(397.05f, 1810.05f);
            rect1196554Path.lineTo(391.28f, 1810.05f);
            rect1196554Path.lineTo(343.78f, 1727.78f);
            rect1196554Path.lineTo(349.55f, 1727.78f);
            rect1196554Path.lineTo(397.05f, 1810.05f);
            rect1196554Path.close();

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor4);
            canvas.drawPath(rect1196554Path, paint);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.07f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(rect1196554Path, paint);
            canvas.restore();

            // path876-1-3-1
            RectF path876131Rect = CacheForCanvas1.path876131Rect;
            path876131Rect.set(307.95f, 1805.72f, 388.78f, 1805.72f);
            Path path876131Path = CacheForCanvas1.path876131Path;
            path876131Path.reset();
            path876131Path.moveTo(388.78f, 1805.72f);
            path876131Path.lineTo(307.95f, 1805.72f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876131Path, paint);
            canvas.restore();

            // path876-3-1-8-5
            RectF path8763185Rect = CacheForCanvas1.path8763185Rect;
            path8763185Rect.set(305.45f, 1801.39f, 386.28f, 1801.39f);
            Path path8763185Path = CacheForCanvas1.path8763185Path;
            path8763185Path.reset();
            path8763185Path.moveTo(386.28f, 1801.39f);
            path8763185Path.lineTo(305.45f, 1801.39f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8763185Path, paint);
            canvas.restore();

            // path876-6-5-8-5
            RectF path8766585Rect = CacheForCanvas1.path8766585Rect;
            path8766585Rect.set(302.95f, 1797.06f, 383.78f, 1797.06f);
            Path path8766585Path = CacheForCanvas1.path8766585Path;
            path8766585Path.reset();
            path8766585Path.moveTo(383.78f, 1797.06f);
            path8766585Path.lineTo(302.95f, 1797.06f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8766585Path, paint);
            canvas.restore();

            // path876-7-9-3-4
            RectF path8767934Rect = CacheForCanvas1.path8767934Rect;
            path8767934Rect.set(300.45f, 1792.73f, 381.28f, 1792.73f);
            Path path8767934Path = CacheForCanvas1.path8767934Path;
            path8767934Path.reset();
            path8767934Path.moveTo(381.28f, 1792.73f);
            path8767934Path.lineTo(300.45f, 1792.73f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8767934Path, paint);
            canvas.restore();

            // path876-5-77-1-9
            RectF path87657719Rect = CacheForCanvas1.path87657719Rect;
            path87657719Rect.set(310.45f, 1810.05f, 391.28f, 1810.05f);
            Path path87657719Path = CacheForCanvas1.path87657719Path;
            path87657719Path.reset();
            path87657719Path.moveTo(391.28f, 1810.05f);
            path87657719Path.lineTo(310.45f, 1810.05f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87657719Path, paint);
            canvas.restore();

            // path876-70-6-8-8
            RectF path87670688Rect = CacheForCanvas1.path87670688Rect;
            path87670688Rect.set(295.45f, 1784.07f, 376.28f, 1784.07f);
            Path path87670688Path = CacheForCanvas1.path87670688Path;
            path87670688Path.reset();
            path87670688Path.moveTo(376.28f, 1784.07f);
            path87670688Path.lineTo(295.45f, 1784.07f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87670688Path, paint);
            canvas.restore();

            // path876-3-9-7-9-3
            RectF path87639793Rect = CacheForCanvas1.path87639793Rect;
            path87639793Rect.set(292.95f, 1779.74f, 373.78f, 1779.74f);
            Path path87639793Path = CacheForCanvas1.path87639793Path;
            path87639793Path.reset();
            path87639793Path.moveTo(373.78f, 1779.74f);
            path87639793Path.lineTo(292.95f, 1779.74f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87639793Path, paint);
            canvas.restore();

            // path876-6-3-3-6-8
            RectF path87663368Rect = CacheForCanvas1.path87663368Rect;
            path87663368Rect.set(290.45f, 1775.41f, 371.28f, 1775.41f);
            Path path87663368Path = CacheForCanvas1.path87663368Path;
            path87663368Path.reset();
            path87663368Path.moveTo(371.28f, 1775.41f);
            path87663368Path.lineTo(290.45f, 1775.41f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87663368Path, paint);
            canvas.restore();

            // path876-7-6-6-4-5
            RectF path87676645Rect = CacheForCanvas1.path87676645Rect;
            path87676645Rect.set(287.95f, 1771.08f, 368.78f, 1771.08f);
            Path path87676645Path = CacheForCanvas1.path87676645Path;
            path87676645Path.reset();
            path87676645Path.moveTo(368.78f, 1771.08f);
            path87676645Path.lineTo(287.95f, 1771.08f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87676645Path, paint);
            canvas.restore();

            // path876-5-0-5-3-2
            RectF path87650532Rect = CacheForCanvas1.path87650532Rect;
            path87650532Rect.set(297.95f, 1788.4f, 378.78f, 1788.4f);
            Path path87650532Path = CacheForCanvas1.path87650532Path;
            path87650532Path.reset();
            path87650532Path.moveTo(378.78f, 1788.4f);
            path87650532Path.lineTo(297.95f, 1788.4f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87650532Path, paint);
            canvas.restore();

            // path876-62-6-3-2
            RectF path87662632Rect = CacheForCanvas1.path87662632Rect;
            path87662632Rect.set(282.95f, 1762.42f, 363.78f, 1762.42f);
            Path path87662632Path = CacheForCanvas1.path87662632Path;
            path87662632Path.reset();
            path87662632Path.moveTo(363.78f, 1762.42f);
            path87662632Path.lineTo(282.95f, 1762.42f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87662632Path, paint);
            canvas.restore();

            // path876-3-6-3-3-2
            RectF path87636332Rect = CacheForCanvas1.path87636332Rect;
            path87636332Rect.set(280.45f, 1758.09f, 361.28f, 1758.09f);
            Path path87636332Path = CacheForCanvas1.path87636332Path;
            path87636332Path.reset();
            path87636332Path.moveTo(361.28f, 1758.09f);
            path87636332Path.lineTo(280.45f, 1758.09f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87636332Path, paint);
            canvas.restore();

            // path876-6-1-9-8-7
            RectF path87661987Rect = CacheForCanvas1.path87661987Rect;
            path87661987Rect.set(277.95f, 1753.76f, 358.78f, 1753.76f);
            Path path87661987Path = CacheForCanvas1.path87661987Path;
            path87661987Path.reset();
            path87661987Path.moveTo(358.78f, 1753.76f);
            path87661987Path.lineTo(277.95f, 1753.76f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87661987Path, paint);
            canvas.restore();

            // path876-7-8-4-6-0
            RectF path87678460Rect = CacheForCanvas1.path87678460Rect;
            path87678460Rect.set(275.45f, 1749.43f, 356.28f, 1749.43f);
            Path path87678460Path = CacheForCanvas1.path87678460Path;
            path87678460Path.reset();
            path87678460Path.moveTo(356.28f, 1749.43f);
            path87678460Path.lineTo(275.45f, 1749.43f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87678460Path, paint);
            canvas.restore();

            // path876-5-7-8-0-3
            RectF path87657803Rect = CacheForCanvas1.path87657803Rect;
            path87657803Rect.set(285.45f, 1766.75f, 366.28f, 1766.75f);
            Path path87657803Path = CacheForCanvas1.path87657803Path;
            path87657803Path.reset();
            path87657803Path.moveTo(366.28f, 1766.75f);
            path87657803Path.lineTo(285.45f, 1766.75f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path87657803Path, paint);
            canvas.restore();

            // path876-70-9-1-4-4
            RectF path876709144Rect = CacheForCanvas1.path876709144Rect;
            path876709144Rect.set(270.45f, 1740.77f, 351.28f, 1740.77f);
            Path path876709144Path = CacheForCanvas1.path876709144Path;
            path876709144Path.reset();
            path876709144Path.moveTo(351.28f, 1740.77f);
            path876709144Path.lineTo(270.45f, 1740.77f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876709144Path, paint);
            canvas.restore();

            // path876-3-9-2-2-8-6
            RectF path876392286Rect = CacheForCanvas1.path876392286Rect;
            path876392286Rect.set(267.95f, 1736.44f, 348.78f, 1736.44f);
            Path path876392286Path = CacheForCanvas1.path876392286Path;
            path876392286Path.reset();
            path876392286Path.moveTo(348.78f, 1736.44f);
            path876392286Path.lineTo(267.95f, 1736.44f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876392286Path, paint);
            canvas.restore();

            // path876-6-3-0-9-8-3
            RectF path876630983Rect = CacheForCanvas1.path876630983Rect;
            path876630983Rect.set(265.45f, 1732.11f, 346.28f, 1732.11f);
            Path path876630983Path = CacheForCanvas1.path876630983Path;
            path876630983Path.reset();
            path876630983Path.moveTo(346.28f, 1732.11f);
            path876630983Path.lineTo(265.45f, 1732.11f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876630983Path, paint);
            canvas.restore();

            // path876-7-6-2-3-8- 2
            RectF path876762382Rect = CacheForCanvas1.path876762382Rect;
            path876762382Rect.set(262.95f, 1727.78f, 343.78f, 1727.78f);
            Path path876762382Path = CacheForCanvas1.path876762382Path;
            path876762382Path.reset();
            path876762382Path.moveTo(343.78f, 1727.78f);
            path876762382Path.lineTo(262.95f, 1727.78f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path876762382Path, paint);
            canvas.restore();

            // path876-5-0-3-90-9-3
            RectF path8765039093Rect = CacheForCanvas1.path8765039093Rect;
            path8765039093Rect.set(272.95f, 1745.1f, 353.78f, 1745.1f);
            Path path8765039093Path = CacheForCanvas1.path8765039093Path;
            path8765039093Path.reset();
            path8765039093Path.moveTo(353.78f, 1745.1f);
            path8765039093Path.lineTo(272.95f, 1745.1f);

            paint.reset();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1.18f);
            canvas.save();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(path8765039093Path, paint);
            canvas.restore();
        }

    }


    // Resizing Behavior
    public static void resizingBehaviorApply(ResizingBehavior behavior, RectF rect, RectF target, RectF result) {
        if (rect.equals(target) || target == null) {
            result.set(rect);
            return;
        }

        if (behavior == ResizingBehavior.Stretch) {
            result.set(target);
            return;
        }

        float xRatio = Math.abs(target.width() / rect.width());
        float yRatio = Math.abs(target.height() / rect.height());
        float scale = 0f;

        switch (behavior) {
            case AspectFit: {
                scale = Math.min(xRatio, yRatio);
                break;
            }
            case AspectFill: {
                scale = Math.max(xRatio, yRatio);
                break;
            }
            case Center: {
                scale = 1f;
                break;
            }
        }

        float newWidth = Math.abs(rect.width() * scale);
        float newHeight = Math.abs(rect.height() * scale);
        result.set(target.centerX() - newWidth / 2,
                target.centerY() - newHeight / 2,
                target.centerX() + newWidth / 2,
                target.centerY() + newHeight / 2);
    }


}