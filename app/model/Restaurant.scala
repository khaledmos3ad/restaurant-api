package model  


  case class Restaurant(uuid: String, data: Data) 
  
  case class Data(
    enName: String,
    arName: String,
    state: String,
    routingMethod: String,
    logo: String,
    enDescription: String,
    arDescription: String,
    shortNumber: String,
    facebookLink: String,
    twitterLink: String,
    youtubeLink: String,
    website: String,
    onlinePayment: Boolean,
    client: Boolean,
    pendingInfo: Boolean,
    pendingMenu: Boolean,
    closed: Boolean
  )
   
  
