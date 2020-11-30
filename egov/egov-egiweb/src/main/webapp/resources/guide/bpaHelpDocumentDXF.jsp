<!DOCTYPE html>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<html>
<head>
<!--Import Google Icon Font-->

<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">

<link rel="stylesheet"
	href="<cdn:url value='/resources/global/css/bts/bts.css'/>">
<link rel="stylesheet"
	href="<cdn:url value='/resources/global/css/font-icons/font-awesome/css/font-awesome.min.css'/>">
<link rel="stylesheet"
	href="<cdn:url value='/resources/global/css/egov/customloginNew.css?rnd=${app_release_no}'/>">
<link rel="stylesheet"
	href="<cdn:url value='/resources/global/css/egov/custom.css?rnd=${app_release_no}'/>">
<link type="text/css" rel="stylesheet"
	href="/egi/resources/guide/css/bpa-features.css">
<link type="text/css" rel="stylesheet" href="/egi/resources/guide/css/materialize.min.css" media="screen,projection">
<link type="text/css" rel="stylesheet" href="/egi/resources/guide/css/landingpage.css">
<!--Let browser know website is optimized for mobile-->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<style>
.icon-header {
	display: block !important;
}
</style>
<script type="text/javascript"
	src="/egi/resources/guide/js/materialize.min.js"></script>
<script type="text/javascript">
	document.addEventListener('DOMContentLoaded', function() {
		var elems = document.querySelectorAll('.collapsible');
		var instances = M.Collapsible.init(elems, {
			inDuration : 0,
			outDuration : 0,
			onOpenStart : function(e) {
				var row = e.querySelectorAll('.lp-row-1');
				row[0].classList.add("row-hide");
				var downarrow = e
						.querySelectorAll('.landingpage-card-down-arrow');
				downarrow[0].classList.add("row-hide");
				var uparrow = e.querySelectorAll('.landingpage-card-up-arrow');
				uparrow[0].classList.remove("row-hide");

			},
			onCloseEnd : function(e) {
				var row = e.querySelectorAll('.lp-row-1');
				row[0].classList.remove("row-hide");
				var downarrow = e
						.querySelectorAll('.landingpage-card-down-arrow');
				downarrow[0].classList.remove("row-hide");
				var uparrow = e.querySelectorAll('.landingpage-card-up-arrow');
				uparrow[0].classList.add("row-hide");

			}
		});

	});
</script>
</head>

<body style="padding-top: 0px">
	<div class="login-content-wrapper col-md-12"
		style="padding-left: 0 !important; padding-right: 0 !important;">

		<div class="new-login-header-wrapper page-common-padding col-md-12">
			<div class="new-login-header padding0 col-md-12">
				<span><img src="/egi/resources/global/images/logo.png"
					height="67" class="rounded"></span>
			</div>
		</div>
	</div>
	<div class="main-content landingpage-font-style" style="padding-top: 129px;">
		<div class="card landingpage-card landingpage-card-video">
			<div class="card-content collapsible-header landingpage-card-content">
				<span class="card-title card-header card-subheader"
					style="text-align: center; color: black; font-weight: 500;">
					Sample DXF Files </span>
			</div>
			<div class="is-flex services-item">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 services"
					style="display: block;">
					<a
						href="https://chstage.blob.core.windows.net/assets/OBPS/Architects/Drawings/MARLA_ALTERATION_1.dxf">
						<div class="content a" style="height: 42px;">Marla
							Alteration</div>
					</a>
				</div>
			</div>
			<div class="is-flex services-item">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 services"
					style="display: block;">
					<a
						href="https://chstage.blob.core.windows.net/assets/OBPS/Architects/Drawings/MARLA_RECONSTRUCTION_1.dxf">
						<div class="content a" style="height: 42px;">Marla
							Reconstruction 1</div>
					</a>
				</div>
			</div>
			<div class="is-flex services-item">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 services"
					style="display: block;">
					<a
						href="https://chstage.blob.core.windows.net/assets/OBPS/Architects/Drawings/MARLA_RECONSTRUCTION_2.dxf">
						<div class="content a" style="height: 42px;">Marla
							Reconstruction 2</div>
					</a>
				</div>
			</div>
			<div class="is-flex services-item">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 services"
					style="display: block;">
					<a
						href="https://chstage.blob.core.windows.net/assets/OBPS/Architects/Drawings/ONE_KANAL_RECONSTRUCTION_1.dxf">
						<div class="content a" style="height: 42px;">One Kanal
							Reconstruction</div>
					</a>
				</div>
			</div>
			<div class="is-flex services-item">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 services"
					style="display: block;">
					<a
						href="https://chstage.blob.core.windows.net/assets/OBPS/Architects/Drawings/ONE_KANAL_RECONSTRUCTION_BASEMENT.dxf">
						<div class="content a" style="height: 42px;">One Kanal
							Reconstructions Basement</div>
					</a>
				</div>
			</div>
			<div class="is-flex services-item">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 services"
					style="display: block;">
					<a
						href="https://chstage.blob.core.windows.net/assets/OBPS/Architects/Drawings/Template_Staircase_V1.dxf">
						<div class="content a" style="height: 42px;">Template
							Staircase v-1</div>
					</a>
				</div>
			</div>
			<div class="is-flex services-item">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 services"
					style="display: block;">
					<a
						href="https://chstage.blob.core.windows.net/assets/OBPS/Architects/Drawings/INNER COURTYARD _SHAFT_TEMPLATE.dxf">
						<div class="content a" style="height: 42px;">Inner Courtyard
							Shaft Template</div>
					</a>
				</div>
			</div>
			<div class="is-flex services-item">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 services"
					style="display: block;">
					<a
						href="https://chstage.blob.core.windows.net/assets/OBPS/Architects/Drawings/Urban_Chandigarh Template V-4.dxf">
						<div class="content a" style="height: 42px;">Urban
							Chandigarh Template V-4</div>
					</a>
				</div>
			</div>
		</div>
	</div>
	<footer
		class="f-regular bpa-login-footer page-common-padding col-md-12 color-black" style="bottom:0; position: fixed;">
		<!-- <div class = "footer-list-content col-md-4">
			<div style = "margin-bottom: 16px;" class = "color-black bpa-common-header2 bpa-common-header">About OBPS</div>
			<div style = "opacity: 0.7;" class = "color-black bpa-common-text">Citizens, Officials, and other stakeholders can submit and track applications in real time and obtain approvals without having to physically visit an office.</div>
		</div> -->
		<div class = "color-black footer-list-content col-md-2">
			<div style="margin-bottom: 16px;" class="color-black bpa-common-header2 bpa-common-header">Help Resources</div>
			<ul class="tutorial-list color-black" >
				
				<li class="bpa-common-text tutorial-item">
					<a href="https://chstage.blob.core.windows.net/assets/OBPS/Architects/UserManuals/User Manual for Architects.pdf" target="_blank"><span class = "color-black bpa-common-text">User Manual for Architects</span></a>
				</li>
				<li class="bpa-common-text tutorial-item">
					<a href="https://chstage.blob.core.windows.net/assets/OBPS/Architects/UserManuals/User Manual for Citizens.pdf" target="_blank"><span class = "color-black bpa-common-text">User Manual for Citizens</span></a>
				</li>
				<li class="bpa-common-text tutorial-item">
					<a href="https://chstage.blob.core.windows.net/assets/OBPS/Architects/UserManuals/Drawing Manual for Architects.pdf" target="_blank"><span class = "color-black bpa-common-text">Drawing Manual for Architects</span></a>
				</li>
				<li class="bpa-common-text tutorial-item">
					<a href="/egi/resources/guide/bpaHelpDocument.jsp" target="_blank"><span class = "color-black bpa-common-text">Training Videos</span></a>
				</li>
				<li class="bpa-common-text tutorial-item">
					<a href="/egi/resources/guide/bpaHelpDocumentDXF.jsp" target="_blank"><span class = "color-black bpa-common-text">Training Drawing</span></a>
				</li>
			</ul>
		</div>
		<div class = "footer-list-content col-md-6"></div>
		<div class = "footer-list-content col-md-4">
			<div style = "margin-bottom: 16px;" class = "color-black bpa-common-header2 bpa-common-header">Get in Touch</div>
			<div class = "footer-contact-info-cont">
				<div class = "color-black bpa-common-text">Chandigarh Smart City Limited,
3rd Floor, Building over the New Bridge (Above SCO 17,18,19) Sector 17-A
Chandigarh 160017</div>
				<div class = "footer-icon-info-cont" style="display:none;">
					<div class = "footer-icon">
						<i class="fa fa-map-marker fa-1x"></i>
					</div>
					<div class = "footer-info">
					<a href="javascript:void(0);" target="_blank">Find us on map</a>
					</div>
				</div>
				<div class = "footer-icon-info-cont">
					<div class = "footer-icon">
						<i class="fa fa-envelope-square fa-1x"></i>
					</div>
					<div class = "footer-info">
						<a href="javascript:void(0);">obps.chd.helpdesk@gmail.com</a>
					</div>
				</div>
				<div class = "footer-icon-info-cont">
					<div class = "footer-icon">
						<i class="fa fa-phone fa-1x"></i>
					</div>
					<div class = "footer-info">
						<a href="javascript:void(0);">0172-2787200</a>
					</div>
				</div>
				
				<div class = "footer-icon-info-cont" style="display:none;">
					<div class = "footer-icon">
						<i class="fa fa-envelope fa-1x"></i>
					</div>
					<div class = "footer-info">
						<a href="mailto:smartcity.chd@nic.in"><p style = "margin: 0">smartcity.chd@nic.in</p></a>
					</div>
				</div>
				
				<div class = "footer-icon-info-cont" style="display:none;">
					<div class = "footer-icon">
						<a href="javascript:void(0);" target="_blank" class="media-link"><i class="fa fa-facebook fa-1x"></i></a>
					</div>
					<div style = "margin-left: 5px;" class = "footer-info">
						<a href="javascript:void(0);" target="_blank" class="media-link"><i class="fa fa-twitter fa-1x"></i></a>
					</div>
				</div>
			</div>
		</div>
	</footer>
</body>

</html>
