declare boundary-space preserve;

declare variable $ntgs as xs:string external;
declare variable $ntas as xs:string external;

declare variable $fg1 as xs:string external;
declare variable $tg1 as xs:string external;
declare variable $pg1 as xs:string external;

declare variable $fg2 as xs:string external;
declare variable $tg2 as xs:string external;
declare variable $pg2 as xs:string external;

declare variable $fg3 as xs:string external;
declare variable $tg3 as xs:string external;
declare variable $pg3 as xs:string external;

declare variable $fa1 as xs:string external;
declare variable $ta1 as xs:string external;
declare variable $pa1 as xs:string external;

declare variable $fa2 as xs:string external;
declare variable $ta2 as xs:string external;
declare variable $pa2 as xs:string external;

declare variable $fa3 as xs:string external;
declare variable $ta3 as xs:string external;
declare variable $pa3 as xs:string external;

declare variable $bgs as xs:string external;
declare variable $bas as xs:string external;
declare variable $bs as xs:string external;

(: SEPARATELY FILTER NODES ACCORDING TO VERTICAL SEARCH PARAMETERS, I.E. FORM, LEMMA, ROOT AND POS :)
declare function local:makequery($node as node()*, $fx as xs:string, $tx as xs:string, $px as xs:string) as node()* {
if ($fx eq 'form')  then
	if($px eq 'ANY') then  
		$node/../w[ft:query(@token,$tx)]
	else
		$node/../w[ft:query(@normalizedform,$tx) and ft:query(./@pos,$px)]
else if($fx eq 'lemma') then
	if($px eq 'ANY') then 
		$node/../w[ft:query(@lemma,$tx)]
	else
		$node/../w[ft:query(@lemma,$tx)  and ft:query(./@pos,$px)]
else if($fx eq 'root') then
	if($px eq 'ANY') then
		$node/../w[ft:query(@root,$tx)]
	else
		$node/../w[ft:query(@root,$tx)  and ft:query(./@pos,$px)]
else
	$node/../w[ft:query(@pos,$px)]
};


let $ntg:=xs:integer($ntgs)
let $nta:=xs:integer($ntas)

let $bg:=if($bgs eq 'true') then true() else false()
let $ba:=if($bas eq 'true') then true() else false()
let $b:=if($bs eq 'true') then true() else false()

(:
let $fg1:="form"
let $tg1:="καὶ"
let $pg1:="ANY"

let $ntg:=1
let $nta:=0

let $bg:=false()
let $ba:=false()
let $b:=false()
:)
(:
let $gr:='analysis_a'
let $ar:='analysis_a'
let $rg0:=/add/doc/field[ft:query(@name,$gr)]/w (: POINTER TO EACH W NODE IN FIELD[@NAME=ANALYSIS_<LANG>] :)
let $ra0:=/add/doc/field[ft:query(@name,$ar)]/w
:)
(: FILTER NODES ACCORDING TO THE BOOLEAN OPERATOR POLICIES: IF THE OPERATOR IS "AND" OR "NEAR", MAKEQUERY CALL PIPES RESULTS 3 TIMES, OTHERWISE, 
   IF THE OPERATOR IS "OR", CALLS ARE INDEPENDENT :) 

(: A SEARCH :)
(:
let $rg1:=if($ntg lt 1) then () else local:makequery($rg0,$fg1,$tg1,$pg1) 	(: conditions on the first query term - Greek :)
let $rg2:=if($ntg lt 2) then () else if ($bg) 							   	(: conditions on the second query term - Greek :)
	then 
		local:makequery($rg1/../w,$fg2,$tg2,$pg2)
	else
		local:makequery($rg0,$fg2,$tg2,$pg2)
let $rg3:=if ($ntg lt 3) then () else if ($bg) then						   	(: conditions on the third query term - Greek :)
		local:makequery($rg2/../w,$fg3,$tg3,$pg3)
	else
		local:makequery($rg0,$fg3,$tg3,$pg3)
:)
(: B SEARCH :)
(:
let $ra1:=if ($nta lt 1) then () else local:makequery($ra0,$fa1,$ta1,$pa1)	(: conditions on the first query term - Arabic :)
let $ra2:=if ($nta lt 2) then () else if ($ba) 								(: conditions on the second query term - Arabic :)
	then 
		local:makequery($ra1/../w,$fa2,$ta2,$pa2)
	else
		local:makequery($ra0,$fa2,$ta2,$pa2)
let $ra3:=if ($nta lt 3) then () else if ($ba) then							(: conditions on the third query term - Arabic :)
		local:makequery($ra2/../w,$fa3,$ta3,$pa3)
	else
		local:makequery($ra0,$fa3,$ta3,$pa3)
:)

(:
let $couplesg:=if ($bg) 
	then
		if($ntg eq 0)
			then
				()
			else if($ntg eq 1) then
				$rg1/../..
			else if($ntg eq 2) then
				$rg1/../.. intersect $rg2/../..
			else
				$rg1/../.. intersect $rg2/../.. intersect $rg3/../..
	else
		$rg1/../.. union $rg2/../.. union $rg3/../..

let $colorg:=$rg1/@id union $rg2/@id union $rg3/@id

let $couplesa:=if ($ba) 
	then 
		if($nta eq 0)
			then
				()
			else if($nta eq 1) then
				$ra1/../..
			else if($nta eq 2) then
				$ra1/../.. intersect $ra2/../..
			else
				$ra1/../.. intersect $ra2/../.. intersect $ra3/../..
	else
		$ra1/../.. union $ra2/../.. union $ra3/../..

let $colora:=$ra1/@id union $ra2/@id union $ra3/@id

let $couples:=if ($b) 
	then 
		if($ntg eq 0) 
			then
				$couplesa
			else 
				if($nta eq 0)
					then
						$couplesg
					else
						$couplesg intersect $couplesa
	else
		$couplesg union $couplesa

:)
(:
return <table width="100%">{
for $couple at $index in $couples
	(:let $pericg:=for $wg in $couple/field[ft:query(@name,'analysis_gr')]/w
			return if ($colorg=$wg/@id) then <span class='hlg' style='background-color:chartreuse' title='{$wg/@lemma/xs:string(.)}'> {$wg/@form/xs:string(.)} </span> else $wg/@form/xs:string(.)
   	let $perica:=for $wa in $couple/field[ft:query(@name,'analysis_ar')]/w
			return if ($colora=$wa/@id) then <span class='hla' style='background-color:yellow' title='voc={$wa/@voc/xs:string(.)} lemma={$wa/@lemma/xs:string(.)} pos={$wa/@pos/xs:string(.)}'> {$wa/@form/xs:string(.)} </span> else $wa/@form/xs:string(.)
	:)		
return 
	<tr class="bk-{$index mod 2}"><p>
		<td class="perica"><b><a href="" title="comment"> {$couple/field[ft:query(@name,'info_a')]/text()}</a> </b>{$couple/field[ft:query(@name,'folio')]/div}</td>
		<td class="pericg">{$couple/field[ft:query(@name,'scans')]/text()}</td>
	</p></tr>
}</table>
:)
let $options :=
    <options>
        <leading-wildcard>yes</leading-wildcard>
    </options>
let  $a := /add/doc/field[@name='analysis_a']/w[ft:query(@token,$ta1,$options)]
return $a/../../field[@name='folio']/div