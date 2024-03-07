# RickAndMortyApp
### RecyclerView

0. Dependências
   implementation("com.github.bumptech.glide:glide:4.16.0") //imagens
   implementation("androidx.recyclerview:recyclerview:1.3.2")
   annotationProcessor("com.github.bumptech.glide:compiler:4.12.0") //imagens


1. Layout do item
2. Adapter
   class CharacterAdapter(private val context: Context
   , private val characterList: List<Character>) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
val textViewName: TextView = view.findViewById(R.id.characterNameTextView)
}

onCreateViewHolder

onBindViewHolder

getItemCount


3. RecyclerView -> activity_main.xml