document.addEventListener('DOMContentLoaded', function ()
{
    const container = document.getElementById('questions-container');
    const addBtn = document.getElementById('add-question-btn');
    let qIndex = 0;

    addBtn.addEventListener('click', function() {
        const qHtml = `<div class="question-block" style="border: 1px solid #ccc; padding: 10px; margin-bottom: 10px;">
                <h4>Pytanie #${qIndex + 1}</h4>
                <label>Treść pytania: <input type="text" name="questions[${qIndex}].content"></label><br>
                <label>Typ: 
                    <select name="questions[${qIndex}].type">
                        <option value="OPEN">Otwarte</option>
                        <option value="MULTI_CHOICE">Wielokrotny wybór</option>
                        <option value="TRUE_FALSE">Prawda/Fałsz</option>
                    </select>
                </label><br>
                <label>Poprawna odp: <input type="text" name="questions[${qIndex}].correctAnswer"></label>
            </div>`;
        container.insertAdjacentHTML('beforehand', qHtml)
        qIndex++;
    });
});