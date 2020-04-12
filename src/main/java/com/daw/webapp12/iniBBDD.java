package com.daw.webapp12;

import com.daw.webapp12.entity.*;
import com.daw.webapp12.entity.Users;
import com.daw.webapp12.repository.AdvertisementRepository;
import com.daw.webapp12.repository.BlogRepository;
import com.daw.webapp12.repository.CommentRepository;
import com.daw.webapp12.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class iniBBDD {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AdvertisementRepository anuncioRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    BlogRepository blogRepository;

    @PostConstruct
    public void init(){

        
        Users users2 = new Users("Edu", "edu@gmail.com","12345678","ROLE_USER");
        userRepository.save(users2);
        Users admin = new Users("admin", "admin@gmail.com","admin","ROLE_ADMIN");
        userRepository.save(admin);
        Users users3 = new Users("Karol","karol@gmail.com","12345678","ROLE_ADMIN");
        userRepository.save(users3);
        Users users4 = new Users("Sebastian","sebastian@gmail.com","12345678","ROLE_USER");
        userRepository.save(users4);
        //Users users5 = new Users("Maria","maria@gmail.com","12345678");
        //userRepository.save(users5);
  
        //Advertisement(String tipo, String vivienda, Integer habitaciones, Integer baños, String metros2, String localizacion, String direccion, double precio)
        Advertisement anun1 = new Advertisement("Venta","Casa",(Integer)4,(Integer)2,120,"Madrid","calle azul,2",(double)200000);
        Comment coment1= new Comment("Hola, me ha encantado");
        Users users5 = new Users("Maria","maria@gmail.com","12345678","ROLE_ADMIN");
        userRepository.save(users5);
        coment1.setAuthor(users5.getName());
        commentRepository.save(coment1);
        anun1.getImages().add("work-1.jpg");
        anun1.getComments().add(coment1);
        anuncioRepository.save(anun1);
        Advertisement anun2 = new Advertisement("Venta","Local",(Integer)2,(Integer)1,50,"Madrid","calle verde,3",(double)120000);
        anun2.getImages().add("work-7.jpg");
        anuncioRepository.save(anun2);
        Advertisement anun3 = new Advertisement("Alquiler","Piso",(Integer)3,(Integer)1,90,"Pontevedra","calle carlos v,4,2 C",(double)1600);
        anun3.getImages().add("work-3.jpg");
        anuncioRepository.save(anun3);
        Advertisement anun4 = new Advertisement("Venta","Casa",(Integer)2,(Integer)1,56,"Madrid","calle verde,3",(double)78990);
        anun4.getImages().add("work-5.jpg");
        anuncioRepository.save(anun4);
        Advertisement anun5 = new Advertisement("Alquiler","Local",(Integer)1,(Integer)1,78,"Madrid","calle verde,3",(double)1200);
        anun5.getImages().add("work-4.jpg");
        anuncioRepository.save(anun5);
        Advertisement anun6 = new Advertisement("Venta","Local",(Integer)4,(Integer)2,85,"Mostoles","calle verde,3",(double)140000);
        anun6.getImages().add("work-7.jpg");
        anuncioRepository.save(anun6);
        Advertisement anun7 = new Advertisement("Alquiler","Local",(Integer)3,(Integer)1,78,"Mostoles","calle verde,3",(double)1200);
        anun7.getImages().add("work-6.jpg");
        anuncioRepository.save(anun7);
        Advertisement anun8 = new Advertisement("Alquiler","Local",(Integer)1,(Integer)1,78,"Valencia","calle verde,3",(double)1000);
        anun8.getImages().add("work-7.jpg");
        anuncioRepository.save(anun8);
        Advertisement anun9 = new Advertisement("Alquiler","Local",(Integer)3,(Integer)1,78,"Ciudad Real","calle verde,3",(double)650);
        anun9.getImages().add("work-2.jpg");
        anuncioRepository.save(anun9);

        Advertisement anun10 = new Advertisement("Alquiler","Local",(Integer)3,(Integer)1,78,"Ciudad Real","calle verde,3",(double)650);
        anun10.getImages().add("work-2.jpg");
        anuncioRepository.save(anun10);

        Search search1 = new Search("Alquiler",(Integer)2,(Integer)1,60,"Madrid",(double)800);
        Search search2 = new Search("Alquiler",(Integer)3,(Integer)2,80,"Mostoles",(double)1000);
        Search search4 = new Search("Alquiler",(Integer)4,(Integer)3,100,"Mostoles",(double)1200);
        Search search5 = new Search("Alquiler",(Integer)2,(Integer)2,70,"Madrid",(double)900);


        Users users1 = new Users("Angel","angel@gmail.com","12345678","ROLE_USER");
        users1.getMyFavourites().add(anun1);
        users1.getMyFavourites().add(anun2);
        users1.getMyFavourites().add(anun3);
        users1.getMyAdvertisements().add(anun4);
        users1.getMyAdvertisements().add(anun6);
        users1.getMyAdvertisements().add(anun7);

        /*users1.getMyAdvertisements().add(anun4);
        users1.getMyAdvertisements().add(anun5);
        users1.getMyAdvertisements().add(anun6);*/

        users1.getMySearches().add(search1);
        users1.getMySearches().add(search2);
        users1.getMySearches().add(search4);
        users1.getMySearches().add(search5);


        userRepository.save(users1);
           

        Blog blog1 = new Blog("CALIDEZ Y CARÁCTER SE ENCUENTRAN FRENTE A FRENTE EN ESTA CASA", "Los propietarios de esta casa unifamiliar en una población cercana a Barcelona —una pareja con hijos adolescentes— querían reformarla para adaptarla a los tiempos modernos. Solo querían un lavado de cara y que les ayudáramos a escoger mobiliario y textiles para modernizar la vivienda.");
        blogRepository.save(blog1);

        Blog blog2 = new Blog("TENDENCIAS EN COCINAS QUE TE VOLVERÁN CRAZY ESTE 2020", "VERDE NATURAL La preocupación por la sostenibilidad ha hecho que los tonos verdes más naturales se cuelen en nuestras cocinas durante este 2020. ACENTOS TURQUESA Los tonos turquesa no solo aportan luminosidad a la cocina, sino también mucha frescura. Además, resaltan un montón con otros colores de base como el blanco, y siempre se ven muy limpios. Ideales para armarios y azulejos.");        
        blogRepository.save(blog2);

        Blog blog3 = new Blog("Piscinas en tu propia casa", "Acercate y prueba");        
        blogRepository.save(blog3);

        Blog blog4 = new Blog("¿Madrid es caro?", "No si alquilas con estas inmobiliarias");        
        blogRepository.save(blog4);

        Blog blog5 = new Blog("Trucos para conseguir el mejor alquiler", "Aprende ya");        
        blogRepository.save(blog5);

        Blog blog6 = new Blog("Alquilar o comprar", "Cual es la mejor decision para ti");        
        blogRepository.save(blog6);

        Blog blog7 = new Blog("Descubre ya tu apartamento ideal", "Primera linea de playa");        
        blogRepository.save(blog7);

        Blog blog8 = new Blog("Casas de hasta tres plantas", "Por solo 1000 euros al mes");        
        blogRepository.save(blog8);

        Blog blog9= new Blog("La revolucion de los alquileres  ha llegado", "Entérate");        
        blogRepository.save(blog9);
    }
}