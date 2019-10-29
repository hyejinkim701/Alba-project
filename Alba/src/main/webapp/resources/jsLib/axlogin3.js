$(function(){
   
// 1.Partimer Login Form  
   $('#pploginf').click(function(){
      $.ajax({
         type:'Post',
         url:'ploginf',
         success:function(result){
            $('#resultArea').html(result);
         } 
      }); // ajax
   }); // click
   

  
// 3.King Login Form 
   
   $('#kkloginf').click(function(){
       $.ajax({
         type:'Post',
         url:'kloginf',
         success:function(result){
            $('#resultArea').html(result);
         } 
      }); // ajax
   }); // click   
   
   $('#mmainf').click(function(){
       $.ajax({
         type:'Post',
         url:'mainf',
         success:function(result){
            $('#resultArea').html(result);
         } 
      }); // ajax
   }); // click   


//소개   
   $('#aaboutf').click(function(){
       $.ajax({
         type:'Post',
         url:'about',
         success:function(result){
            $('#resultArea').html(result);
         } 
      }); // ajax
   }); // click   

 //찾아오는길  
   $('#mmap').click(function(){
       $.ajax({
         type:'Post',
         url:'map',
         success:function(result){
            $('#resultArea').html(result);
         } 
      }); // ajax
   }); // click   

   
   
  
}) // ready