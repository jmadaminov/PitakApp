import com.badcompany.domain.domainmodel.UserCredentials


fun userLogin() = "token"

fun testUserCredentials() = UserCredentials(999999999999, "test")

//fun getExercise(
//    name: String = "Barbell Bench Press",
//    sets: List<Set> = listOf(getSet()),
//    type: ExerciseType = ExerciseType.BARBELL
//) = Exercise(
//    name,
//    sets,
//    type
//)
//
//fun getSet(
//    repsOrTime: Int = 7,
//    weight: String = "145 lbs"
//) = Set(
//    repsOrTime,
//    weight
//)
//
//fun getExerciseTemplate(
//    name: String = "Barbell Bench Press",
//    targets: List<ExerciseTarget> = listOf(
//        ExerciseTarget.CHEST,
//        ExerciseTarget.DELTOID_FRONT,
//        ExerciseTarget.TRICEPS
//    ),
//    isCustom: Boolean = false,
//    type: ExerciseType = ExerciseType.BARBELL
//) = ExerciseTemplate(
//    name,
//    targets,
//    isCustom,
//    type
//)
//
//fun getWorkoutTemplate(
//    id: Int = 123456789,
//    name: String = "International Chest Day",
//    thumbnailRes: String = "chest_dae.png",
//    description: String = "Down, up, down, up, down, up, come on more energy!",
//    difficulty: Int = 3,
//    isCustom: Boolean = false,
//    targets: List<ExerciseTarget> = listOf(ExerciseTarget.CHEST),
//    workout: Workout = getWorkout()
//
//) =
//    WorkoutTemplate(
//        id,
//        name,
//        thumbnailRes,
//        description,
//        difficulty,
//        isCustom,
//        targets,
//        workout
//    )
