<!DOCTYPE html>
<html>
	<head>
		<link type="text/css" rel="stylesheet" href="/egi/resources/guide/css/bpa-features.css" />
		 <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	    <!--Import materialize.css-->
	    <link type="text/css" rel="stylesheet" href="/egi/resources/guide/css/materialize.min.css" media="screen,projection" />
	    <link type="text/css" rel="stylesheet" href="/egi/resources/guide/css/landingpage.css" />    
	    <style>
	        .icon-header {display: block !important;}
	    </style>
	    <script type="text/javascript" src="/egi/resources/guide/js/materialize.min.js"></script>
	    <script type="text/javascript">
	        document.addEventListener('DOMContentLoaded', function () {
	            var elems = document.querySelectorAll('.collapsible');
	            var instances = M.Collapsible.init(elems, {
	                inDuration: 0,
	                outDuration: 0,
	                onOpenStart: function (e) {
	                    var row = e.querySelectorAll('.lp-row-1');
	                    row[0].classList.add("row-hide");
	                    var downarrow = e.querySelectorAll('.landingpage-card-down-arrow');
	                    downarrow[0].classList.add("row-hide");
	                    var uparrow = e.querySelectorAll('.landingpage-card-up-arrow');
	                    uparrow[0].classList.remove("row-hide");	
	                },
	                onCloseEnd: function (e) {
	                    var row = e.querySelectorAll('.lp-row-1');
	                    row[0].classList.remove("row-hide");
	                    var downarrow = e.querySelectorAll('.landingpage-card-down-arrow');
	                    downarrow[0].classList.remove("row-hide");
	                    var uparrow = e.querySelectorAll('.landingpage-card-up-arrow');
	                    uparrow[0].classList.add("row-hide");	
	                }
	            });
	        });
	    </script>   
	</head>
<body>
	<div class="landingpage-font-style">
		<div class="card landingpage-card">
			<ul class="collapsible card-collapse">
				<li style="padding-left: 40px; padding-right: 40px;" class="">
					<div class="card-content collapsible-header landingpage-card-content" tabindex="0">
						<span class="card-title card-header">About Building Plan Approval System</span>
					</div>
					<div class="row lp-row-1 collapsible-header landingpage-card-content" tabindex="0">
						<div class="col s12 m2">
							<img class="header-icon" src="/egi/resources/guide/assets/ICONS/BPA_1.png">
							<p class="landingpage-card-icon-description">End to end Process Digitisation</p>
						</div>
						<div class="col s12 m2 lp-col-margin">
							<img class="header-icon" src="/egi/resources/guide/assets/ICONS/BPA_2.png">
							<p class="landingpage-card-icon-description">Single window for multiple services</p>
						</div>
						<div class="col s12 m2 lp-col-margin">
							<img class="header-icon" src="/egi/resources/guide/assets/ICONS/BPA_3.png">
							<p class="landingpage-card-icon-description">Open source smart platform</p>
						</div>
						<div class="col s12 m2 lp-col-margin">
							<img class="header-icon" src="/egi/resources/guide/assets/ICONS/BPA_4.png">
							<p class="landingpage-card-icon-description">Achieving Transparency</p>
						</div>
						<div class="col s12 m2 lp-col-margin">
							<img class="header-icon" src="/egi/resources/guide/assets/ICONS/BPA_5.png">
							<p class="landingpage-card-icon-description">Seamless Application Processes</p>
						</div>
					</div>
					<div class="row collapsible-header collapsible-body card-body" tabindex="0" style="">
						<div class="col s12 m2">
							<img class="header-icon" src="/egi/resources/guide/assets/ICONS/BPA_1.png">
							<p class="landingpage-card-icon-description">End to end Process Digitisation</p>
	
							<div class="landingpage-card-description"> Scrutiny reports are produced in the matter of minutes with automatic rule mapping. The development regulation process is also automated saving paperwork and time of stakeholders. 
							</div>
						</div>
						<div class="col s12 m2 lp-col-margin">
							<img class="header-icon" src="/egi/resources/guide/assets/ICONS/BPA_2.png">
							<p class="landingpage-card-icon-description">Single window for multiple services</p>
	
							<div class="landingpage-card-description">Stakeholders like ULB officials, architects, town planners, engineers and citizens are connected on a single platform to coordinate various services. Stakeholders are updated in  real time of the application progress thus achieving transparency.
						  
							</div>
						</div>
						<div class="col s12 m2 lp-col-margin">
							<img class="header-icon" src="/egi/resources/guide/assets/ICONS/BPA_3.png">
							<p class="landingpage-card-icon-description">Open source smart platform</p>
	
							<div class="landingpage-card-description">It is a state-of-the-art, ERP-based platform created for urban governance, built on advanced open source technologies ensuring flexibility, interoperability and faster implementation.
									 </div>
						</div>
						<div class="col s12 m2 lp-col-margin">
							<img class="header-icon" src="/egi/resources/guide/assets/ICONS/BPA_4.png">
							<p class="landingpage-card-icon-description">Achieving Transparency</p>
							<div class="landingpage-card-description">Existing manual processes are completely replaced with automated workflows. Standardisation of processes with well-defined roles and responsibilities of stakeholders mapped in the system. MIS reports are automatically generated to help track process efficiency.</div>
						</div>
	
						<div class="col s12 m2 lp-col-margin">
							<img class="header-icon" src="/egi/resources/guide/assets/ICONS/BPA_5.png">
							<p class="landingpage-card-icon-description">Seamless Application Processes</p>
	
							<div class="landingpage-card-description">Earlier processes which were tedious have been replaced with  a seamless submission process for Building Plan Licensing systems through auto synchronisation of data between DIGIT DCR and BPA application.</div>
						</div>
					</div>
					<div class="row collapsible-header card-action card-collapse-header" tabindex="0">
						<i class="material-icons landingpage-card-down-arrow">keyboard_arrow_down</i>
						<i class="material-icons landingpage-card-up-arrow row-hide">keyboard_arrow_up</i>
					</div>
				</li>
			</ul>
		</div>
		<div class="card landingpage-card">
			<ul class="collapsible card-collapse">
				<li style="padding-left: 40px; padding-right: 40px;">
					<div class="card-content collapsible-header landingpage-card-content" tabindex="0">
						<span class="card-title card-header">Citizen &amp; Building Licensee </span>
					</div>
					<div class="row collapsible-header card-body" tabindex="0">
						<div class="col s12 m4 ">
							<img class="header-icon" src="/egi/resources/guide/assets/ICONS/licensee_1.png">
							<p class="landingpage-card-icon-description">Online payment</p>
							<div class="landingpage-card-description">Citizens and Building Licensees can make an online payment at anytime and from anywhere. Online payments can be made using debit/credit card/internet banking.
							</div>
						</div>
						<div class="col s12 m4">
							<img class="header-icon" src="/egi/resources/guide/assets/ICONS/citizen_1.png">
							<p class="landingpage-card-icon-description">Track Application</p>
							<p class="landingpage-card-description">The citizen as well as building licensee can receive real time updates on submitted application. The applicant will be provided with the login credentials to the user portal.
							</p>
						</div>
						<div class="col s12 m4">
							<img class="header-icon" src="/egi/resources/guide/assets/ICONS/citizen_2.png">
							<p class="landingpage-card-icon-description">Receive alert and notification</p>
							<p class="landingpage-card-description">All stakeholders including citizens, business users, and ULB officials are on one platform. Real time synchronisation of the entire process including status updates for every required interaction between end users via SMS and email notifications.
							</p>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</div>
		
	<footer class = "f-regular bpa-login-footer page-common-padding col-md-12 color-black">
		<div class = "footer-list-content col-md-4">
			<div style = "margin-bottom: 16px;" class = "color-black bpa-common-header2 bpa-common-header">About OBPS</div>
			<div style = "opacity: 0.7;" class = "color-black bpa-common-text">Citizens, Officials, and other stakeholders can submit and track applications in real time and obtain approvals without having to physically visit an office.</div>
		</div>
		<div class = "color-black footer-list-content col-md-4">
			<div style="margin-bottom: 16px;display: none;" class="color-black bpa-common-header2 bpa-common-header">Help Resources</div>
			<ul class="tutorial-list color-black" style="display: none;">
				<li class="bpa-common-text tutorial-item">
					<a href="javascript:void(0);" target="_blank">Registered building licensee user manual</a>
				</li>
				<li class="bpa-common-text tutorial-item">
					<a href="javascript:void(0);" target="_blank">DIGIT DCR user manual</a>
				</li>
				<li class="bpa-common-text tutorial-item">
					<a href="javascript:void(0);" target="_blank">Citizen Help Manual</a>
				</li>
				<li class=" bpa-common-text tutorial-item">
					<a href="javascript:void(0);" target="_blank">Fee Details</a>
				</li>
				<li class="bpa-common-text tutorial-item">
					<a href="javascript:void(0);" target="_blank">Single family residential building layers</a>
				</li>
				 <li class="bpa-common-text tutorial-item">
					<a href="javascript:void(0);" target="_blank">Layer Matrix for multiple occupancies</a>
				</li>
				<li class="bpa-common-text tutorial-item">
					<a href="javascript:void(0);" target="_blank">Layer Set (las format)</a>
				</li>
				<li class="bpa-common-text tutorial-item">
					<a href="javascript:void(0);" target="_blank">Single family residential building - Drawing Guidelines</a>
				</li> 
			</ul>
		</div>
		
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
						<i class="fa fa-phone fa-1x"></i>
					</div>
					<div class = "footer-info">
						<a href="javascript:void(0);">0172-504-3196</a>
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