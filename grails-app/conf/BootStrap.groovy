class BootStrap {
     def mongo

     def init = { servletContext ->
        println "===> mongo: $mongo"
     }
     def destroy = {
     }
} 
