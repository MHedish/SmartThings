/**
 *  Copyright 2015 Stuart Buchanan
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *	AsusWRT TV Checker
 *
 *	Author: Stuart Buchanan, Based on original work by midyear66 with thanks
 *	This app recieves a HHTP Get request from a AsusWrt Router when a Smart TV Connects & Disconnects from the Wifi Network
 *	Date: 2016-02-03 v1.0 Initial Release
 */
definition(
    name: "AsusWRT TV Checker",
    namespace: "fuzzysb",
    author: "Stuart Buchanan",
    description: "Triggers Virtual Switch when HTTP GET Request is recieved",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
    section(title: "Select Devices") {
        input "virtualSwitch", "capability.Switch", title: "Select Virtual Switch", required: true, multiple:false
    }
    
}

def installed() {
	createAccessToken()
	getToken()
	DEBUG("Installed Phone with rest api: $app.id")
    DEBUG("Installed Phone with token: $state.accessToken")
    DEBUG("Installed with settings: $virtualSwitch.name")
}
def updated() {
	DEBUG("Updated Phone with rest api: $app.id")
    DEBUG("Updated Phone with token: $state.accessToken")
}


mappings {
  path("/TV/on") {
    action: [
      GET: "switchon"
    ]
  }
  path("/TV/off") {
    action: [
      GET:"switchoff"
    ]
  }
}


// Callback functions
def getSwitch() {
    // This returns the current state of the switch in JSON
    return virtualSwitch.currentState("switch")
}

def switchon() {
        	DEBUG("On")
            virtualSwitch.on();
}

def switchoff() {
        	DEBUG("Off")
            virtualSwitch.off();
}

def getToken(){
if (!state.accessToken) {
		try {
			getAccessToken()
			DEBUG("Creating new Access Token: $state.accessToken")
		} catch (ex) {
			DEBUG("Did you forget to enable OAuth in SmartApp IDE settings")
            DEBUG(ex)
		}
	}
}

private def DEBUG(message){
	log.debug message
}