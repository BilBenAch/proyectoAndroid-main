package com.example.proyectoandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectoandroid.databinding.FragmentAnadirProductoBinding;
import com.example.proyectoandroid.model.Producto;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AnadirProductoFragment extends BaseFragment {
    private FragmentAnadirProductoBinding binding;
    private AppViewModel appViewModel;
    Executor executor = Executors.newSingleThreadExecutor();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentAnadirProductoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        List<Producto> productos = Arrays.asList(
                new Producto("Monty", "abrigo hombre", "blanco", 520.00, "hombre", Arrays.asList(R.drawable.abrigo2, R.drawable.abrigo3, R.drawable.abrigo4)),
                new Producto("Anna big", "abrigo hombre", "azul", 320.00, "hombre", Arrays.asList(R.drawable.abrigohombrebugattiazul1, R.drawable.abrigohombrebugattiazul2, R.drawable.abrigohombrebugattiazul3)),
                new Producto("Quicksilver", "abrigo hombre", "verde", 120.00, "hombre", Arrays.asList(R.drawable.abrigohombrequicksilververde1, R.drawable.abrigohombrequicksilververde2, R.drawable.abrigohombrequicksilververde3)),
                new Producto("Gafas Armani", "complementos hombre", "dorado", 587.00, "hombre", Arrays.asList(R.drawable.complementoshombregafasarmanidoradas1, R.drawable.complementoshombregafasarmanidoradas2, R.drawable.complementoshombregafasarmanidoradas3)),
                new Producto("Reloj BMW", "complementos hombre", "plateado", 1200.00, "hombre", Arrays.asList(R.drawable.complementoshombrerelojdoradobmw1, R.drawable.complementoshombrerelojdoradobmw2, R.drawable.complementoshombrerelojdoradobmw3)),
                new Producto("Mascarilla GUESS", "complementos hombre", "negro", 70.00, "hombre", Arrays.asList(R.drawable.complementoshombremascarillaguessnegra1, R.drawable.complementoshombremascarillaguessnegra2, R.drawable.complementoshombremascarillaguessnegra3)),
                new Producto("Flow G adidas", "camisetas hombre", "amarillo", 70.00, "hombre", Arrays.asList(R.drawable.camisetaadidasamarillo1, R.drawable.camisetaadidasamarillo2, R.drawable.camisetaadidasamarillo3)),
                new Producto("Mquina Emporio", "camisetas hombre", "negro", 320.00, "hombre", Arrays.asList(R.drawable.camisetaemporionegro1, R.drawable.camisetaemporionegro2, R.drawable.camisetaemporionegro3)),
                new Producto("OWWMAMA Pier", "camisetas hombre", "blanco", 578.00, "hombre", Arrays.asList(R.drawable.camisetapieroneblanco1, R.drawable.camisetapieroneblanco2, R.drawable.camisetapieroneblanco3)),
                new Producto("Bambas champion", "calzado hombre", "blanco", 120.00, "hombre", Arrays.asList(R.drawable.calzadohombrechampionblanco1, R.drawable.calzadohombrechampionblanco2, R.drawable.calzadohombrechampionblanco3)),
                new Producto("Bambas nike one", "calzado hombre", "blanco", 300.00, "hombre", Arrays.asList(R.drawable.calzadohombrenikeblancas1, R.drawable.calzadohombrenikeblancas2, R.drawable.calzadohombrenikeblancas3)),
                new Producto("Bambas nike one byo", "calzado hombre", "negro", 420.00, "hombre", Arrays.asList(R.drawable.calzadohombrenikenegro1, R.drawable.calzadohombrenikenegro2, R.drawable.calzadohombrenikenegro4)),
                new Producto("Benet G star", "pantalones hombre", "azul", 1800.00, "hombre", Arrays.asList(R.drawable.pantaloneshombregstarazul1, R.drawable.pantaloneshombregstarazul2, R.drawable.pantaloneshombregstarazul3)),
                new Producto("Style Imagin", "pantalones hombre", "negro", 1500.00, "hombre", Arrays.asList(R.drawable.pantaloneshombreimaginneegros1, R.drawable.pantaloneshombreimaginneegros2, R.drawable.pantaloneshombreimaginneegros3)),
                new Producto("Favit Big L", "pantalones hombre", "amarillo", 700.00, "hombre", Arrays.asList(R.drawable.pantaloneshombrepanaamarillo1, R.drawable.pantaloneshombrepanaamarillo2, R.drawable.pantaloneshombrepanaamarillo3)),
                new Producto("Boxers Calvin klein", "ropa interior hombre", "morado", 50.00, "hombre", Arrays.asList(R.drawable.ropainteriorhombrecalbinkleinmorado1, R.drawable.ropainteriorhombrecalbinkleinmorado2, R.drawable.ropainteriorhombrecalbinkleinmorado3)),
                new Producto("Boxers Pier", "ropa interior hombre", "gris", 50.00, "hombre", Arrays.asList(R.drawable.ropainteriorhombrepierdolegris1, R.drawable.ropainteriorhombrepierdolegris2, R.drawable.ropainteriorhombrepierdolegris3)),
                new Producto("Criterio Gucci", "vestido", "verde", 5000.00, "mujer", Arrays.asList(R.drawable.vestidogucciverde1, R.drawable.vestidogucciverde2, R.drawable.vestidogucciverde3)),
                new Producto("Deficit Mijstalipo", "vestido", "azul", 7000.00, "mujer", Arrays.asList(R.drawable.vestidomodernazul1, R.drawable.vestidomodernazul2, R.drawable.vestidomodernazul3)),
                new Producto("LIGA Prada", "vestido", "negro", 10000.00, "mujer", Arrays.asList(R.drawable.vestidopradanegro1, R.drawable.vestidopradanegro2, R.drawable.vestidopradanegro3)),
                new Producto("Luna Olivi", "camiseta mujer", "verde", 200.00, "mujer", Arrays.asList(R.drawable.camisetamujeroliviverde1, R.drawable.camisetamujeroliviverde2, R.drawable.camisetamujeroliviverde3)),
                new Producto("Pepe Jeans", "camiseta mujer", "rosa", 210.00, "mujer", Arrays.asList(R.drawable.camisetamujerpepejeansrosa1, R.drawable.camisetamujerpepejeansrosa2, R.drawable.camisetamujerpepejeansrosa3)),
                new Producto("Tomy Hilfiger", "camiseta mujer", "blanca", 310.00, "mujer", Arrays.asList(R.drawable.camisetamujertommyblanca1, R.drawable.camisetamujertommyblanca2, R.drawable.camisetamujertommyblanca3)),
                new Producto("Addidas", "pantalones mujer", "azul", 200.00, "mujer", Arrays.asList(R.drawable.pantalonesmujeradidasazul1, R.drawable.pantalonesmujeradidasazul2, R.drawable.pantalonesmujeradidasazul3)),
                new Producto("Guess", "pantalones mujer", "rosa", 700.00, "mujer", Arrays.asList(R.drawable.pantalonesmujerguessrosa1, R.drawable.pantalonesmujerguessrosa2, R.drawable.pantalonesmujerguessrosa3)),
                new Producto("Only", "pantalones mujer", "azul", 800.00, "mujer", Arrays.asList(R.drawable.pantalonesmujeronlytejano1, R.drawable.pantalonesmujeronlytejano2, R.drawable.pantalonesmujeronlytejano3)),
                new Producto("Poppa", "faldas", "gris", 150.00, "mujer", Arrays.asList(R.drawable.faldabiggris1, R.drawable.faldabiggris2, R.drawable.faldabiggris3)),
                new Producto("Boss", "faldas", "negro", 400.00, "mujer", Arrays.asList(R.drawable.faldahugonegro1, R.drawable.faldahugonegro2, R.drawable.faldahugonegro3)),
                new Producto("Internationality", "faldas", "verde", 550.00, "mujer", Arrays.asList(R.drawable.faldaintellverde1, R.drawable.faldaintellverde2, R.drawable.faldaintellverde3)),
                new Producto("Anna pierre", "bolsos", "negro", 550.00, "mujer", Arrays.asList(R.drawable.bolsoananegro1, R.drawable.bolsoananegro2, R.drawable.bolsoananegro3)),
                new Producto("Badatino", "bolsos", "marron", 1550.00, "mujer", Arrays.asList(R.drawable.bolsobalamarron1, R.drawable.bolsobalamarron2, R.drawable.bolsobalamarron3)),
                new Producto("Botas Bima Badatino", "calzado mujer", "negro", 1550.00, "mujer", Arrays.asList(R.drawable.calzadomujerabimbanegro1, R.drawable.calzadomujerabimbanegro2, R.drawable.calzadomujerabimbanegro3)),
                new Producto("Botas Cooler Bambino", "calzado mujer", "marron", 800.00, "mujer", Arrays.asList(R.drawable.calzadomujercooolmarron1, R.drawable.calzadomujercooolmarron2, R.drawable.calzadomujercooolmarron3)),
                new Producto("Timona balancino", "calzado mujer", "azul", 850.00, "mujer", Arrays.asList(R.drawable.calzadomujertimonazul1, R.drawable.calzadomujertimonazul2, R.drawable.calzadomujertimonazul3)),
                new Producto("Conjunto Calvin Klein", "ropa interior mujer", "negro", 50.00, "mujer", Arrays.asList(R.drawable.ropainteriormujercalvinkleinnegro1, R.drawable.ropainteriormujercalvinkleinnegro2, R.drawable.ropainteriormujercalvinkleinnegro3)),
                new Producto("Puma fachera", "ropa interior mujer", "rosa", 50.00, "mujer", Arrays.asList(R.drawable.ropainteriormujerpumarosa1, R.drawable.ropainteriormujerpumarosa2, R.drawable.ropainteriormujerpumarosa3))
        );
        appViewModel.insertar(productos);



        MutableLiveData<Boolean> finishedLoading = new MutableLiveData<>();


        finishedLoading.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                navController.navigate(R.id.action_global_bottom_home_fragment2);
            }
        });

        // esto deberia estar en el Model y llamarlo a traves del ViewModel

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // simular la carga de recursos
                    Thread.sleep(1000);
                    finishedLoading.postValue(true);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}